app.controller("account-ctrl", function($scope, $http, $window){
	$scope.items =[]
	$scope.form ={}
	$scope.isError=false;
	$scope.isUpdate=false;
	
	//Lấy danh sách tài khoản
	$scope.initialize = function(){
		$http.get(`/rest/accounts`).then(resp =>{
			$scope.items = resp.data
			console.log("Success get accounts")
		}).catch(error =>{
			console.log("Error get accounts", error)
		})
	}
	
	//Chọn tài khoản
	$scope.edit = function(item){
		$scope.isError=false
		$scope.message=""
		$scope.form = angular.copy(item)
		$(".nav-tabs a:eq(0)").tab("show")
	}
	
	//reset form
	$scope.reset = function(){
		$scope.form ={}
		$scope.isError=false
		$scope.message=""
		$scope.isUpdate=false
		$window.scrollTo(0, 0);
	}
	
	//Hàm chạy khi submit
	$scope.SaveOrUpdate = function(){
		if($scope.isUpdate){ //isUpdate = true thì update, ngược lại create
			$scope.update()
		} else{
			$scope.create()
		}
	}
	
	//Hàm update
	$scope.update = function(){
		var item = angular.copy($scope.form)
		item.password = item.password.trim()
		$http.put(`/rest/accounts`, item).then(resp =>{
			var index = $scope.items.findIndex(account => account.username == resp.data.username)
			$scope.items[index] = resp.data
			$scope.form = resp.data
			$scope.isError =false
			$scope.message =""
			alert("Cập nhật tài khoản thành công!")
			console.log("Success update account")
		}).catch(error =>{
			console.log("Error update account", error)
		})
	}
	
	//Hàm create
	$scope.create = function(){
		var item = angular.copy($scope.form)
		console.log(item.username)
		$http.post(`/rest/accounts`, item).then(resp =>{
			var index = $scope.items.findIndex(account => account.username == resp.data.username)
			$scope.items.push(resp.data)
			$scope.reset()
			alert("Tạo tài khoản thành công!")
			console.log("Success create account")
		}).catch(error =>{
			console.log("Error create account", error)
		})
	}
	
	//Bắt lỗi update, k có lỗi thì set isUpdate = true
	$scope.setUpdate = function(){
		if($scope.form.username!=null){ //Nếu đã nhập username
			var item = $scope.items.find(account =>account.username.trim() == $scope.form.username.trim())
			if(item!=null){ //account có tồn tại
				$scope.isUpdate = true;
			} else{ //account k tồn tại
				$scope.isUpdate = false
				$scope.isError = true
				$scope.message = "Tài khoản chưa được tạo!"
				$window.scrollTo(0, 0);
			}
		} else{
			$scope.isUpdate = true //Chưa nhập username đổi type nút Sửa thành submit để Angularjs bắt lỗi submit
		}
	}
	
	//Bắt lỗi create, k có lỗi thì set isUpdate = false
	$scope.setCreate = function(){
		if($scope.form.username!=null){ //Nếu đã nhập username
			var item = $scope.items.find(account =>account.username.trim() == $scope.form.username.trim())
			if(item==null){
				$scope.isUpdate = false
			} else{
				$scope.isUpdate = true
				$scope.isError = true
				$scope.message ="Tài khoản đã tồn tại!"
				$window.scrollTo(0, 0);
			}
		} else{
			$scope.isUpdate = false //Chưa nhập username đổi type nút Thêm thành submit để Angularjs bắt lỗi submit
		}
	}
	
	//Phân trang
	$scope.pager = {
		page: 0,
		size: 4,
		get items(){
			var start = this.page * this.size;
			return $scope.items.slice(start, start + this.size)
		},
		get count(){
			return Math.ceil(1.0*$scope.items.length/this.size)
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
	
})