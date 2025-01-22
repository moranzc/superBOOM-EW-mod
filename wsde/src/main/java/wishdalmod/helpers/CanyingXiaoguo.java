package wishdalmod.helpers;

public class CanyingXiaoguo {
    DifangXiaoguo canyingxiaoguo;
    public int onGiveCanying(int amount) {
        if (this.canyingxiaoguo != null) {
            amount = this.canyingxiaoguo.onGiveCanying(amount);
        }
        return amount;
    }
}