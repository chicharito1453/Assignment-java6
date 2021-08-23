app.controller("order-ctrl", function($scope, $http, $window){
	$scope.orders =[]
	$scope.form ={}
	$scope.isError = false
	
	//Lấy dữ liệu
	$scope.initialize = function(){
		//Lấy danh sách đơn hàng
		$http.get(`/rest/orders`).then(resp =>{
			$scope.orders = resp.data
			$scope.orders.forEach(order =>{
				order.createDate = new Date(order.createDate)
				order.orderDetails =[]
			})
			console.log("Success get orders")
		}).catch(error =>{
			console.log("Error get orders", error)
		});
		
		//Lấy chi tiết đơn hàng
		$http.get(`/rest/orderDetails`).then(resp =>{
			for(let i=0;i<$scope.orders.length;i++){
				var list =[]
				for(let j=0;j<resp.data.length;j++){
					if(resp.data[j].order.id == $scope.orders[i].id){
						list.push(resp.data[j]) 
					}
				}
				$scope.orders[i].orderDetails = list
			}
			console.log("Success get orderDetails")
		}).catch(error =>{
			console.log("Error get orderDetails", error)
		});
	};
	
	//chọn đơn hàng
	$scope.edit = function(item){
		$scope.form = angular.copy(item)
		$(".nav-tabs a:eq(0)").tab("show")
	}
	
	//reset form
	$scope.reset = function(){
		$scope.form =[]
		$scope.isError = false
		$window.scrollTo(0,0)
	};
	
	//Cập nhật đơn hàng
	$scope.update = function(){
		var item = angular.copy($scope.form)
		if($scope.form.id != null){
			$http.put(`/rest/orders`, item).then(resp =>{
				var index = $scope.orders.findIndex(order => order.id == item.id)
				$scope.orders[index] = item
				$scope.form = item
				alert("Cập nhật đơn hàng thành công!")
				console.log("Success update order")
			}).catch(error =>{
				console.log("Error update order", error)
			});
		} else {
			$scope.isError = true;
			$window.scrollTo(0, 0);
		}
	};
	
	//Xóa đơn hàng
	$scope.delete = function(item){
		if($scope.form.id != null){
			if(confirm("Bạn có chắc xóa đơn hàng này?")){
				$http.delete(`/rest/orders/${item.id}`, item).then(resp =>{
					var index = $scope.orders.findIndex(order => order.id == item.id)
					$scope.orders.splice(index,1)
					$scope.reset()
					alert("Xóa đơn hàng thành công!")
					console.log("Success delete order")
				}).catch(error =>{
					console.log("Error delete order", error)
				})
			}
		} else {
			$scope.isError = true;
			$window.scrollTo(0, 0);
		}
	};
	
	//Phân trang
	$scope.pager = {
		page: 0,
		size: 4,
		get items(){
			var start = this.page * this.size;
			return $scope.orders.slice(start, start + this.size)
		},
		get count(){
			return Math.ceil(1.0*$scope.orders.length/this.size)
		},
		get totalPages(){
			var range = []
			for(var i=0;i<this.count;i++) { 
				   range.push(i); 
			} 
			return range;
		},
		getPage(i){
			this.page = i;
		}
	};
	
	$scope.initialize();
	
});