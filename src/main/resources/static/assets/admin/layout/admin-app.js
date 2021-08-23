const app = angular.module("admin-app",["ngRoute"]);

//ROUTE GẮN TEMPLATE
app.config(function($routeProvider){
    $routeProvider
    .when("/",{
        templateUrl: "/assets/admin/layout/home.html"
    })
    .when("/admin/product",{
        templateUrl: "/assets/admin/product/index.html",
    	controller: "product-ctrl"
    })
    .when("/admin/account",{
        templateUrl: "/assets/admin/account/index.html",
    	controller: "account-ctrl"
    })
    .when("/admin/category",{
    	templateUrl: "/assets/admin/category/index.html",
    	controller: "category-ctrl"
    })
    .when("/admin/order",{
    	templateUrl: "/assets/admin/order/index.html",
    	controller: "order-ctrl"
    })
    .when("/admin/report",{
    	templateUrl: "/assets/admin/report/index.html",
    	controller: "report-ctrl"
    })
    .when("/admin/authorize",{
    	templateUrl: "/assets/admin/authority/index.html",
    	controller: "authority-ctrl"
    })
});

