package store;

import store.controller.StoreController;

public class Application {
    public static void main(String[] args) {
        AppConfig appConfig = new AppConfig();
        appConfig.initController().initData();
        StoreController storeController = appConfig.storeController();
        storeController.orderProcess();
    }
}
