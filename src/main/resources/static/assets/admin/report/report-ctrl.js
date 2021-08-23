app.controller("report-ctrl",function($scope,$http){
	$scope.categories=[];
	$scope.products=[];
	$scope.years=[];
	
	//Lấy thống kê nhãn hàng
	$scope.getCategories = function(){
		$http.get(`/rest/report/categories`).then(resp =>{
			$scope.categories = resp.data
			console.log("Success get categories")
		}).catch(error =>{
			console.log("Error get categories", error)
		})
	}
	
	//Lấy thống kê năm
	$scope.getYears = function(){
		$http.get(`/rest/report/years`).then(resp =>{
			$scope.years = resp.data
			console.log("Success get years")
		}).catch(error =>{
			console.log("Error get years", error)
		})
	}
	
	//Lấy thống kê sản phẩm
	$scope.getProducts = function(){
		$http.get(`/rest/report/products`).then(resp =>{
			$scope.products = resp.data
			console.log("Success get products")
		}).catch(error =>{
			console.log("Error get products", error)
		})
	}
	
	//Phân trang nhãn hàng
	$scope.pagerC = {
		page: 0,
		size: 4,
		get items(){
			var start = this.page * this.size;
			return $scope.categories.slice(start, start + this.size)
		},
		get count(){
			return Math.ceil(1.0*$scope.categories.length/this.size)
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
	
	//Phân trang sản phẩm
	$scope.pagerP = {
		page: 0,
		size: 5,
		get items(){
			var start = this.page * this.size;
			return $scope.products.slice(start, start + this.size)
		},
		get count(){
			return Math.ceil(1.0*$scope.products.length/this.size)
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
	
	$scope.getCategories();
	$scope.getProducts();
	$scope.getYears();
	
});