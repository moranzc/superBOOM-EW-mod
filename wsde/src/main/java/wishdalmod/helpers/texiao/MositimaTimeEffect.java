package wishdalmod.helpers.texiao;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import wishdalmod.powers.MositimaPower;

import java.util.ArrayList;

public class MositimaTimeEffect extends AbstractGameEffect {
    private static final Color PURE_BLUE_COLOR = new Color(0.1f, 0.3f, 1.0f, 0.25f);
    private static final Color TIME_BLUE = new Color(0.2f, 0.4f, 1.0f, 1.0f);
    private static final Color TIME_LIGHT_BLUE = new Color(0.6f, 0.8f, 1.0f, 1.0f);

    private final ArrayList<TimeDigit> digits = new ArrayList<>();
    private final ArrayList<TimeParticle> particles = new ArrayList<>();
    private final AbstractCreature target;
    private float hourAngle = 0f;
    private float minuteAngle = 0f;
    private float particleTimer;
    private float particleSpawnTimer;
    private final TextureRegion whiteTex;
    private final TextureRegion[] romanTextures = new TextureRegion[12];
    private final TextureRegion clockFaceTex;
    private final TextureRegion circleTex;
    private boolean powerActive = true;
    private float clockBreathTime = 0f;
    private float globalTime = 0f;

    // 时针/分针拖尾效果
    private final ArrayList<HandTrail> hourHandTrails = new ArrayList<>();
    private final ArrayList<HandTrail> minuteHandTrails = new ArrayList<>();
    private float trailTimer;

    // 罗马数字最大尺寸
    private static final float MAX_ROMAN_SIZE = 128f * Settings.scale;

    public MositimaTimeEffect(AbstractCreature target) {
        this.target = target;
        this.renderBehind = true;
        this.duration = 1000000f; // 长时间持续
        this.whiteTex = new TextureRegion(ImageMaster.WHITE_SQUARE_IMG);
        this.circleTex = new TextureRegion(ImageMaster.GLOW_SPARK_2);
        this.particleTimer = 0.2f;
        this.particleSpawnTimer = 0.05f;
        this.trailTimer = 0.03f;

        // 加载表盘背景图
        this.clockFaceTex = new TextureRegion(
                ImageMaster.loadImage("wishdaleResources/images/dongtu/mositima/clock_face.png")
        );

        // 加载罗马数字贴图
        for (int i = 0; i < 12; i++) {
            romanTextures[i] = new TextureRegion(
                    ImageMaster.loadImage("wishdaleResources/images/dongtu/mositima/roman_" + (i + 1) + ".png")
            );
        }
    }

    public void updatePowerStatus() {
        this.powerActive = (target != null && target.hasPower(MositimaPower.POWER_ID));
    }

    @Override
    public void update() {
        updatePowerStatus();
        if (!powerActive) {
            isDone = true;
            return;
        }

        globalTime += Gdx.graphics.getDeltaTime();
        clockBreathTime += Gdx.graphics.getDeltaTime();
        hourAngle -= Gdx.graphics.getDeltaTime() * 15f;
        minuteAngle -= Gdx.graphics.getDeltaTime() * 90f;

        // 更新拖尾效果计时器
        trailTimer -= Gdx.graphics.getDeltaTime();
        if (trailTimer < 0) {
            trailTimer = 0.03f;
            addHandTrail(true); // 时针拖尾
            addHandTrail(false); // 分针拖尾
        }

        // 更新拖尾粒子
        updateHandTrails(hourHandTrails);
        updateHandTrails(minuteHandTrails);

        // 更新罗马数字粒子
        particleTimer -= Gdx.graphics.getDeltaTime();
        if (particleTimer < 0) {
            particleTimer = 0.3f;
            addNewDigit();
        }

        // 更新时间粒子
        particleSpawnTimer -= Gdx.graphics.getDeltaTime();
        if (particleSpawnTimer < 0) {
            particleSpawnTimer = 0.05f;
            addTimeParticle();
        }

        // 更新罗马数字
        for (int i = digits.size() - 1; i >= 0; i--) {
            TimeDigit digit = digits.get(i);
            digit.update();
            if (digit.isDone) {
                digits.remove(i);
            }
        }

        // 更新时间粒子
        for (int i = particles.size() - 1; i >= 0; i--) {
            TimeParticle particle = particles.get(i);
            particle.update();
            if (particle.isDone) {
                particles.remove(i);
            }
        }
    }

    private void updateHandTrails(ArrayList<HandTrail> trails) {
        for (int i = trails.size() - 1; i >= 0; i--) {
            HandTrail trail = trails.get(i);
            trail.update();
            if (trail.isDone) {
                trails.remove(i);
            }
        }
    }

    private void addHandTrail(boolean isHourHand) {
        float clockSize = 300f * Settings.scale;
        float centerX = target.hb.cX;
        float centerY = target.hb.cY - 50f * Settings.scale;

        float length = isHourHand ? clockSize * 0.35f : clockSize * 0.5f;
        float width = isHourHand ? clockSize * 0.03f : clockSize * 0.02f;
        float angle = isHourHand ? hourAngle : minuteAngle;

        Color trailColor = isHourHand ?
                new Color(0.8f, 0.8f, 1.0f, 0.7f) :
                new Color(0.9f, 0.9f, 1.0f, 0.7f);

        ArrayList<HandTrail> trailList = isHourHand ? hourHandTrails : minuteHandTrails;
        trailList.add(new HandTrail(centerX, centerY, angle, length, width, trailColor));
    }

    private void addNewDigit() {
        // 只生成在屏幕下半部分
        float minY = 0;                       // 屏幕底部
        float maxY = Settings.HEIGHT / 2;     // 屏幕中间

        float x = MathUtils.random(0, Settings.WIDTH);
        float y = MathUtils.random(minY, maxY);
        int index = MathUtils.random(0, 11);
        digits.add(new TimeDigit(x, y, index));
    }

    private void addTimeParticle() {
        // 在表盘周围生成粒子
        float clockSize = 300f * Settings.scale;
        float centerX = target.hb.cX;
        float centerY = target.hb.cY - 50f * Settings.scale;

        // 随机角度和距离
        float angle = MathUtils.random(0f, 360f);
        float distance = MathUtils.random(clockSize * 0.6f, clockSize * 1.2f);

        float x = centerX + distance * MathUtils.sinDeg(angle);
        float y = centerY + distance * MathUtils.cosDeg(angle);

        particles.add(new TimeParticle(x, y));
    }

    public void render(SpriteBatch sb) {
        if (!powerActive) return;

        renderGradientBackground(sb);
        renderTimeDistortion(sb);

        for (TimeDigit digit : digits) {
            digit.render(sb);
        }

        for (TimeParticle particle : particles) {
            particle.render(sb);
        }

        renderClock(sb);

        // 渲染拖尾效果
        for (HandTrail trail : hourHandTrails) {
            trail.render(sb);
        }
        for (HandTrail trail : minuteHandTrails) {
            trail.render(sb);
        }
    }

    private void renderTimeDistortion(SpriteBatch sb) {
        float intensity = 0.3f + 0.1f * MathUtils.sin(globalTime * 3f);
        int segments = 24;
        float radius = Settings.WIDTH * 0.8f;
        float centerX = target.hb.cX;
        float centerY = target.hb.cY - 50f * Settings.scale;

        sb.setBlendFunction(770, 1); // 使用加法混合增强发光效果

        for (int i = 0; i < segments; i++) {
            float angle = i * (360f / segments) + globalTime * 10f;
            float rad = angle * MathUtils.degRad;
            float dist = radius * (0.9f + 0.1f * MathUtils.sin(globalTime * 2f + i * 0.5f));

            float startX = centerX + dist * MathUtils.sin(rad);
            float startY = centerY + dist * MathUtils.cos(rad);

            float endX = centerX + (dist + Settings.WIDTH * 0.1f) * MathUtils.sin(rad);
            float endY = centerY + (dist + Settings.WIDTH * 0.1f) * MathUtils.cos(rad);

            float alpha = 0.3f * intensity * (0.7f + 0.3f * MathUtils.sin(globalTime * 4f + i));
            Color color = new Color(
                    0.3f + 0.2f * MathUtils.sin(globalTime + i),
                    0.5f + 0.3f * MathUtils.sin(globalTime * 1.5f + i),
                    1.0f,
                    alpha
            );

            renderLine(sb, startX, startY, endX, endY, Settings.WIDTH * 0.002f, color);
        }

        sb.setBlendFunction(770, 771); // 恢复默认混合模式
    }

    private void renderGradientBackground(SpriteBatch sb) {
        int segments = 20;
        float totalHeight = Settings.HEIGHT * 3f / 5f;
        float segmentHeight = totalHeight / segments;
        float waveOffset = globalTime * 0.5f;

        for (int i = 0; i < segments; i++) {
            float waveFactor = 0.2f * MathUtils.sin(i * 0.3f + waveOffset);
            float alpha = (1f - (float) i / segments) * 0.3f;
            Color segmentColor = new Color(
                    PURE_BLUE_COLOR.r + waveFactor * 0.1f,
                    PURE_BLUE_COLOR.g + waveFactor * 0.1f,
                    PURE_BLUE_COLOR.b,
                    alpha
            );
            sb.setColor(segmentColor);
            sb.draw(ImageMaster.WHITE_SQUARE_IMG,
                    0,
                    i * segmentHeight,
                    Settings.WIDTH,
                    segmentHeight);
        }
    }

    private void renderClock(SpriteBatch sb) {
        float breathScale = 1.0f + 0.05f * MathUtils.sin(clockBreathTime * 2f);
        float clockSize = 300f * Settings.scale * breathScale;
        float x = target.hb.cX;
        float y = target.hb.cY - 50f * Settings.scale;
        float alpha = 0.9f;

        // 表盘发光效果 - 外发光
        float glowSize = clockSize * 1.3f;
        float glowAlpha = 0.5f + 0.2f * MathUtils.sin(globalTime * 2f);
        sb.setBlendFunction(770, 1); // 加法混合
        sb.setColor(0.3f, 0.5f, 1.0f, glowAlpha * 0.3f);
        sb.draw(ImageMaster.GLOW_SPARK_2,
                x - glowSize / 2f,
                y - glowSize / 2f,
                glowSize,
                glowSize);

        // 表盘发光效果 - 内发光
        glowSize = clockSize * 1.1f;
        sb.setColor(0.6f, 0.8f, 1.0f, glowAlpha * 0.4f);
        sb.draw(ImageMaster.GLOW_SPARK_2,
                x - glowSize / 2f,
                y - glowSize / 2f,
                glowSize,
                glowSize);
        sb.setBlendFunction(770, 771); // 恢复默认混合

        // 绘制表盘背景图片
        sb.setColor(1f, 1f, 1f, alpha);
        sb.draw(clockFaceTex,
                x - clockSize / 2f,
                y - clockSize / 2f,
                clockSize / 2f,
                clockSize / 2f,
                clockSize,
                clockSize,
                1f, 1f,
                0f);

        // 绘制表盘边框（增强发光效果）
        float borderSize = clockSize * 1.05f;
        sb.setColor(0.8f, 0.8f, 1.0f, alpha * 0.8f);
        drawCircle(sb, x, y, borderSize / 2);

        // 绘制刻度线（增强发光效果）
        renderClockMarkings(sb, x, y, clockSize, alpha);

        // 时针
        float hourHandLength = clockSize * 0.35f;
        float hourHandWidth = clockSize * 0.03f;
        renderClockHand(sb, x, y, hourAngle, hourHandLength, hourHandWidth,
                new Color(0.8f, 0.8f, 1.0f, alpha));

        // 分针
        float minuteHandLength = clockSize * 0.5f;
        float minuteHandWidth = clockSize * 0.02f;
        renderClockHand(sb, x, y, minuteAngle, minuteHandLength, minuteHandWidth,
                new Color(0.9f, 0.9f, 1.0f, alpha));

        // 中心点（增强发光效果）
        float centerSize = clockSize * 0.04f;
        sb.setColor(1.0f, 1.0f, 1.0f, alpha);
        sb.draw(ImageMaster.GLOW_SPARK_2,
                x - centerSize / 2f,
                y - centerSize / 2f,
                centerSize / 2f,
                centerSize / 2f,
                centerSize,
                centerSize,
                1.0f, 1.0f,
                0);
    }

    private void drawCircle(SpriteBatch sb, float centerX, float centerY, float radius) {
        float diameter = radius * 2;
        sb.draw(circleTex,
                centerX - radius,
                centerY - radius,
                radius,
                radius,
                diameter,
                diameter,
                1.0f, 1.0f,
                0);
    }

    private void renderClockMarkings(SpriteBatch sb, float centerX, float centerY, float size, float alpha) {
        float markingLength = size * 0.05f;
        float markingWidth = size * 0.01f;
        float innerRadius = size * 0.4f;
        Color markingColor = new Color(1.0f, 1.0f, 1.0f, alpha * 0.9f);

        for (int i = 0; i < 12; i++) {
            float angle = i * 30;
            float rad = angle * MathUtils.degRad;
            float startX = centerX + innerRadius * MathUtils.sin(rad);
            float startY = centerY + innerRadius * MathUtils.cos(rad);
            float endX = centerX + (innerRadius + markingLength) * MathUtils.sin(rad);
            float endY = centerY + (innerRadius + markingLength) * MathUtils.cos(rad);
            renderLine(sb, startX, startY, endX, endY, markingWidth, markingColor);
        }
    }

    private void renderLine(SpriteBatch sb, float startX, float startY,
                            float endX, float endY, float width, Color color) {
        float dx = endX - startX;
        float dy = endY - startY;
        float rotation = MathUtils.atan2(dy, dx) * MathUtils.radDeg;
        float length = (float) Math.sqrt(dx * dx + dy * dy);
        Color originalColor = sb.getColor();
        sb.setColor(color);
        sb.draw(whiteTex,
                startX,
                startY - width / 2,
                0,
                width / 2,
                length,
                width,
                1.0f, 1.0f,
                rotation);
        sb.setColor(originalColor);
    }

    private void renderClockHand(SpriteBatch sb, float centerX, float centerY,
                                 float angle, float length, float width, Color color) {
        float rad = angle * MathUtils.degRad;
        float endX = centerX + length * MathUtils.sin(rad);
        float endY = centerY + length * MathUtils.cos(rad);
        float dx = endX - centerX;
        float dy = endY - centerY;
        float rotation = MathUtils.atan2(dy, dx) * MathUtils.radDeg;
        sb.setColor(color);
        sb.draw(whiteTex,
                centerX,
                centerY - width / 2,
                0,
                width / 2,
                length,
                width,
                1.0f, 1.0f,
                rotation);
    }

    @Override
    public void dispose() { }

    private class TimeDigit {
        float x, y;
        float scale;
        float alpha;
        float velX, velY;
        float rotation;
        float rotationSpeed;
        boolean isDone;
        float lifeTime;
        float fadeInTime = 0.3f;
        float fadeOutTime = 1.8f;
        float maxLifeTime = fadeInTime + fadeOutTime;
        int numeralIndex;
        float floatOffset;
        float floatTime;
        float glowIntensity;

        public TimeDigit(float x, float y, int index) {
            this.x = x;
            this.y = y;
            this.numeralIndex = index;

            // 确保罗马数字最大为128x128（原图大小）
            // 计算缩放因子，使最大尺寸不超过128像素
            float maxScale = MAX_ROMAN_SIZE / Math.max(
                    romanTextures[index].getRegionWidth(),
                    romanTextures[index].getRegionHeight()
            );

            // 随机缩放因子，但不超过最大缩放
            this.scale = MathUtils.random(0.3f, 1.0f) * maxScale;

            this.velX = MathUtils.random(-1.5f, 1.5f) * Settings.scale;
            this.velY = MathUtils.random(-1.0f, 1.0f) * Settings.scale;
            this.rotation = 0;
            this.rotationSpeed = MathUtils.random(-5f, 5f);
            this.lifeTime = 0f;
            this.floatOffset = MathUtils.random(0f, 100f);
            this.glowIntensity = MathUtils.random(0.3f, 0.8f);
        }

        public void update() {
            lifeTime += Gdx.graphics.getDeltaTime();
            floatTime += Gdx.graphics.getDeltaTime();

            if (lifeTime < fadeInTime) {
                alpha = lifeTime / fadeInTime;
            } else if (lifeTime < maxLifeTime) {
                float fadeProgress = (lifeTime - fadeInTime) / fadeOutTime;
                alpha = 1.0f - fadeProgress;
            } else {
                isDone = true;
                return;
            }

            x += velX * Gdx.graphics.getDeltaTime();
            y += velY * Gdx.graphics.getDeltaTime();
            rotation += rotationSpeed * Gdx.graphics.getDeltaTime();

            // 添加上下浮动效果
            y += MathUtils.sin(floatTime * 2f + floatOffset) * 0.5f * Settings.scale;

            float minY = 0;                     // 屏幕底部
            float maxY = Settings.HEIGHT / 2;   // 屏幕中间
            if (y < minY) y = minY;
            if (y > maxY) y = maxY;

            // 左右边界循环
            if (x < 0) x = Settings.WIDTH;
            if (x > Settings.WIDTH) x = 0;
        }

        public void render(SpriteBatch sb) {
            if (numeralIndex < 0 || numeralIndex >= romanTextures.length) return;
            TextureRegion tex = romanTextures[numeralIndex];

            // 渲染发光效果
            sb.setBlendFunction(770, 1); // 加法混合
            float glowScale = scale * 1.2f;
            float glowAlpha = alpha * glowIntensity * 0.5f;
            sb.setColor(0.4f, 0.6f, 1.0f, glowAlpha);
            sb.draw(tex,
                    x - tex.getRegionWidth() / 2f * glowScale,
                    y - tex.getRegionHeight() / 2f * glowScale,
                    tex.getRegionWidth() / 2f * glowScale,
                    tex.getRegionHeight() / 2f * glowScale,
                    tex.getRegionWidth() * glowScale,
                    tex.getRegionHeight() * glowScale,
                    1f, 1f,
                    rotation);

            sb.setBlendFunction(770, 771); // 恢复默认混合

            // 渲染数字主体
            sb.setColor(1f, 1f, 1f, alpha);
            sb.draw(tex,
                    x - tex.getRegionWidth() / 2f * scale,
                    y - tex.getRegionHeight() / 2f * scale,
                    tex.getRegionWidth() / 2f * scale,
                    tex.getRegionHeight() / 2f * scale,
                    tex.getRegionWidth() * scale,
                    tex.getRegionHeight() * scale,
                    1f, 1f,
                    rotation);
        }
    }

    private class TimeParticle {
        float x, y;
        float size;
        float alpha;
        float rotation;
        float rotationSpeed;
        boolean isDone;
        float lifeTime;
        float maxLifeTime;
        Color color;
        Vector2 velocity;
        float floatOffset;

        public TimeParticle(float x, float y) {
            this.x = x;
            this.y = y;
            this.size = MathUtils.random(2f, 8f) * Settings.scale;
            this.alpha = MathUtils.random(0.4f, 0.8f);
            this.rotation = MathUtils.random(0f, 360f);
            this.rotationSpeed = MathUtils.random(-180f, 180f);
            this.lifeTime = 0f;
            this.maxLifeTime = MathUtils.random(1.0f, 2.0f);
            this.floatOffset = MathUtils.random(0f, 100f);

            // 随机颜色 - 蓝色系
            float hue = MathUtils.random(0.5f, 0.7f);
            this.color = new Color(
                    hue * 0.4f,
                    hue * 0.8f,
                    1.0f,
                    alpha
            );

            // 随机速度朝向表盘中心
            float angle = MathUtils.random(0f, 360f);
            float speed = MathUtils.random(10f, 50f) * Settings.scale;
            this.velocity = new Vector2(
                    speed * MathUtils.sinDeg(angle),
                    speed * MathUtils.cosDeg(angle)
            );
        }

        public void update() {
            lifeTime += Gdx.graphics.getDeltaTime();
            if (lifeTime > maxLifeTime) {
                isDone = true;
                return;
            }

            // 粒子运动
            x += velocity.x * Gdx.graphics.getDeltaTime();
            y += velocity.y * Gdx.graphics.getDeltaTime();
            rotation += rotationSpeed * Gdx.graphics.getDeltaTime();

            // 淡出效果
            alpha = 1.0f - (lifeTime / maxLifeTime);

            // 上下浮动
            y += MathUtils.sin(lifeTime * 5f + floatOffset) * 0.8f * Settings.scale;
        }

        public void render(SpriteBatch sb) {
            sb.setColor(color.r, color.g, color.b, alpha);
            sb.draw(ImageMaster.GLOW_SPARK,
                    x - size / 2f,
                    y - size / 2f,
                    size / 2f,
                    size / 2f,
                    size,
                    size,
                    1.0f, 1.0f,
                    rotation);
        }
    }

    private class HandTrail {
        float x, y;
        float length;
        float width;
        float rotation;
        float alpha;
        boolean isDone;
        float lifeTime;
        float maxLifeTime = 0.3f;
        Color color;

        public HandTrail(float centerX, float centerY, float angle, float length, float width, Color color) {
            this.length = length;
            this.width = width;
            this.rotation = angle;
            this.color = new Color(color);
            this.alpha = color.a;

            // 计算位置 - 从中心点开始
            float rad = angle * MathUtils.degRad;
            this.x = centerX;
            this.y = centerY;

            lifeTime = 0f;
        }

        public void update() {
            lifeTime += Gdx.graphics.getDeltaTime();
            if (lifeTime > maxLifeTime) {
                isDone = true;
                return;
            }

            // 淡出效果
            alpha = color.a * (1.0f - lifeTime / maxLifeTime);

            // 缩短长度
            length *= 0.95f;
        }

        public void render(SpriteBatch sb) {
            sb.setColor(color.r, color.g, color.b, alpha);

            float rad = rotation * MathUtils.degRad;
            float endX = x + length * MathUtils.sin(rad);
            float endY = y + length * MathUtils.cos(rad);

            float dx = endX - x;
            float dy = endY - y;
            float rot = MathUtils.atan2(dy, dx) * MathUtils.radDeg;

            sb.draw(whiteTex,
                    x,
                    y - width / 2,
                    0,
                    width / 2,
                    (float) Math.sqrt(dx * dx + dy * dy),
                    width,
                    1.0f, 1.0f,
                    rot);
        }
    }
}