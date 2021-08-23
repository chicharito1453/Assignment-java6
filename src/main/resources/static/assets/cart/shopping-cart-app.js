app = angular.module("shopping-cart-app", []);

app.controller("shopping-cart-ctrl", function($scope, $http){
    $scope.cart ={
        items:[],
        //Thêm sp
        add(id){
            var item = this.items.find(item => item.id == id);
            if(item){
                item.qty++;
                this.saveToLocalStorage();
                alert("Sản phẩm đã được vào giỏ hàng!");
            }
            else{
                $http.get(`/rest/products/${id}`).then(resp => {
                    resp.data.qty = 1;
                    this.items.push(resp.data);
                    this.saveToLocalStorage();
                    alert("Sản phẩm đã được vào giỏ hàng!");
                });
            }
        },
        //Xóa sp
        remove(id){
            var index = this.items.findIndex(item => item.id == id);
            this.items.splice(index,1);
            this.saveToLocalStorage();
        },
        //xóa sạch mặt hàng
        clear(){
            this.items = [];
            this.saveToLocalStorage();
        },
        //Tổng tiền 1 sp
        amt_of(item){},
        //Tổng số lượng các mặt hàng
        get count(){
            return this.items
                        .map(item => item.qty)
                        .reduce((total,qty) =>total += qty,0)
        },
        //Tổng tiền
        get amount(){
            return this.items
                        .map(item => item.qty*(item.price-(item.price*item.discount/100)))
                        .reduce((total,qty) =>total += qty,0)
        },
        //Lưu giỏ hàng vào Local Storage
        saveToLocalStorage(){
            var json = JSON.stringify(angular.copy(this.items));
            localStorage.setItem("cart", json);
        },
        loadFromLocalStorage(){
            var json = localStorage.getItem("cart");
            this.items = json ? JSON.parse(json):[];
        },
        BuyButton(id){
        	this.add(id);
        	location.href = "/cart"
        }
    }
    $scope.cart.loadFromLocalStorage();
    
    $scope.order = {
            createDate: new Date(),
            address: "",
            account:{username:$("#username").text()},
            get orderDetails(){
                return $scope.cart.items.map(item =>{
                    return {
                        product:{id :item.id},
                        price:item.price-(item.price*item.discount/100),
                        quantity:item.qty
                    }
                })
            },
            total:$scope.cart.amount,
            status:false,
            purchase(){
                var order = angular.copy(this);
                //Thực hiện đât hàng
                $http.post("/rest/orders", order).then(resp =>{
                    alert("Đặt hàng thành công!")
                    $scope.cart.clear();
                    location.href = "/orderDetail/" + resp.data.id;
                }).catch(error =>{
                    alert("Đặt hàng bị lỗi!")
                    console.log(error)
                })
            }
        }
    $scope.category = {
    	items:[],
    	load_all_Cate(){
    		$http.get(`/rest/categories`).then(resp => {
    			for(var i in resp.data){
                	resp.data[i].checked = false;
                }
                this.items = resp.data;
                this.saveCateToSS();
                console.log("Success", resp);
            }).catch(error => {
                console.log("Error", error);
            });
    	},
    	saveItem(id){
    		var item = this.items.find(item => item.id == id);
    		if(item.checked == true){
    			item.checked = false;
    		}
    		else{
    			item.checked = true;
    		}
    		 this.saveCateToSS();
    	},
    	saveCateToSS(){
            var json = JSON.stringify(angular.copy(this.items));
            sessionStorage.setItem("cate", json);
        },
        loadCateFromSS(){
            var json =  sessionStorage.getItem("cate");
            if(json === null){
            	this.load_all_Cate();
            }
            else{
            	this.items = JSON.parse(json);
            }
        }
    }
    $scope.category.loadCateFromSS();
    
    $scope.searchP = {
    	items:[],
    	load_all_searchP(){
    		$http.get(`/rest/products`).then(resp => {
                this.items = resp.data;
                console.log("Success", resp);
            }).catch(error => {
                console.log("Error", error);
            });
    	}
    }
    $scope.searchP.load_all_searchP();
    
});