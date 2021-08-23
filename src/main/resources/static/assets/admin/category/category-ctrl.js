app.controller("category-ctrl",function($scope, $http){
	$scope.items =[];
	$scope.form ={};
	$scope.isError =false;
	$scope.isUpdate=false;
	$scope.message ="";
	
	//Lấy danh sách nhãn hàng
	$scope.initialize = function(){
		$http.get(`/rest/categories`).then(resp =>{
			$scope.items = resp.data
			console.log("Succes get categories")
		}).catch(error =>{
			console.log("Error get categories", error)
		});
	};
	
	$scope.edit = function(item){
		$scope.isError =false
		$scope.message =""
		$scope.form = angular.copy(item)
		$(".nav-tabs a:eq(0)").tab("show")
	};
	
	$scope.reset = function(){
		$scope.isError =false
		$scope.isUpdate=false
		$scope.message =""
		$scope.form ={}
	};
	
	//Hàm chạy khi submit
	$scope.SaveOrUpdate = function(){ 
		if($scope.isUpdate){ //isUpdate = true thì update, ngược lại create
			$scope.update()
		}
		else{
			$scope.create()
		}
	}
	
	//Hàm tạo nhãn hàng
	$scope.create = function(){
		var item = angular.copy($scope.form)
		$http.post(`/rest/categories`, item).then(resp =>{
			$scope.items.push(item)
			$scope.reset()
			sessionStorage.removeItem('cate');
			alert("Tạo nhãn hàng thành công!")
			console.log("Success create category")
		}).catch(error =>{
			console.log("Error create category", error)
		});
	};
	
	//Hàm cập nhật nhãn hàng
	$scope.update = function(){
		var item = angular.copy($scope.form)
		$http.put(`/rest/categories`, item).then(resp =>{
			var index = $scope.items.findIndex(cate =>cate.id==resp.data.id)
			$scope.items[index] = resp.data
			$scope.isError = false
			$scope.message =""
			sessionStorage.removeItem('cate');
			alert("Cập nhật nhãn hàng thành công!")
			console.log("Success update category")
		}).catch(error =>{
			console.log("Error update category", error)
		});
	};
	
	//Bắt lỗi update, k có lỗi thì set isUpdate = true
	$scope.setUpdate = function(){
		if($scope.form.id!=null){ //Nếu đã nhập id
			var item = $scope.items.find(idC => idC.id.trim() == $scope.form.id.trim())
			if(item!=null){ //nhãn hàng có tồn tại
				$scope.isUpdate=true
			} else{ //Nhãn hàng k tồn tại
				$scope.isUpdate=false
				$scope.message ="Nhãn hàng chưa được tạo!"
				$scope.isError =true	
			}
		} else{
			$scope.isUpdate=true //Chưa nhập id đổi type nút Sửa thành submit để Angularjs bắt lỗi submit
		}
	};
	
	//Bắt lỗi create, k có lỗi thì set isUpdate = false
	$scope.setCreate = function(){
		if($scope.form.id!=null){ //Nếu đã nhập id
			var item = $scope.items.find(idC => idC.id.trim() == $scope.form.id.trim()) //kiểm tra nhãn hàng đã tồn tại hay chưa
			if(item==null){ //Chưa tồn tại thì tạo
				$scope.isUpdate=false
			} else{ //Đã tồn tại báo lỗi
				$scope.isUpdate=true
				$scope.message ="Nhãn hàng đã được tạo trước đó!"
				$scope.isError =true
			}
		} else{
			$scope.isUpdate=false //Chưa nhập id đổi type nút Thêm thành submit để Angularjs bắt lỗi submit
		}
	};
	
	//Xòa
	$scope.delete = function(form){
		if($scope.form.id!=null){
			var item = $scope.items.find(idC => idC.id.trim() == form.id.trim()) //kiểm tra nhãn hàng đã tồn tại hay chưa
			if(item!=null){
				$http.delete(`/rest/categories/${item.id}`).then(resp =>{
					if(resp.data){
						var index = $scope.items.findIndex(p =>p.id == item.id)
						$scope.items.splice(index,1)
						$scope.reset()
						alert("Xóa nhãn hàng thành công!")
					} else{
						alert("Nhãn hàng đã có sản phẩm, không thể xóa!")
					}
					console.log("Success progressing delete category")
				}).catch(error =>{
					console.log("Error delete category", error)
				})
			} else{
				$scope.message ="Nhãn hàng chưa được tạo!"
				$scope.isError =true
			}
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
	
});