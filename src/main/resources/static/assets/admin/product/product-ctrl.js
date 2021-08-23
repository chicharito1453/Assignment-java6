app.controller("product-ctrl",function($scope,$http,$window){
	$scope.form={};
	$scope.items=[];
	$scope.categories=[];
	$scope.isError=false;
	$scope.isUpdate=false;
	
	$scope.initialize = function(){
		//Lấy danh sách sản phẩm
		$http.get(`/rest/products`).then(resp =>{
			$scope.items = resp.data
			$scope.items.forEach(item =>{
				item.createDate = new Date(item.createDate)
			})
			console.log("Success get products")
		}).catch(error =>{
			console.log("Error get products", error)
		});
		//Lấy danh sách nhãn hàng
		$http.get(`/rest/categories`).then(resp =>{
			$scope.categories = resp.data
			$scope.form.category=$scope.categories[0] //tránh option đầu tiên của select bị rỗng
			console.log("Success get categories")
		}).catch(error =>{
			console.log("Error get categories", error)
		});
	};
	$scope.initialize();
	
	//Chọn sản phẩm
	$scope.edit = function(item){
		$scope.reset()
		$scope.form = angular.copy(item);
		$(".nav-tabs a:eq(0)").tab("show")
	};
	
	//Reset form
	$scope.reset = function(){
		$scope.isUpdate=false;
		$scope.isError=false;
		$scope.form={};
		$("input").value = '';
		$scope.form.category=$scope.categories[0]//tránh option đầu tiên của select bị rỗng
		$window.scrollTo(0, 0);
	}
	
	//Tạo sản phẩm
	$scope.create = function(){
		var item = angular.copy($scope.form)
		item.id = null; //id tự tăng nên set null
		$http.post(`/rest/products`, item).then(resp =>{
			resp.data.createDate = new Date(resp.data.createDate)
			$scope.items.push(resp.data)
			$scope.reset()
			alert("Thêm sản phẩm mới thành công!")
			console.log("Success create product")
		}).catch(error =>{
			alert("Thêm sản phẩm mới thất bại!")
			console.log("Error create product", error)
		});
	};
	
	//Cập nhật sản phẩm
	$scope.update = function(){
		var item = angular.copy($scope.form)
		$http.put(`/rest/products`, item).then(resp =>{
			var index = $scope.items.findIndex(p =>p.id == item.id)
			$scope.items[index] =item
			$window.scrollTo(0, 0);
			alert("Cập nhật sản phẩm thành công!")
			console.log("Success update product")
		}).catch(error =>{
			alert("Cập nhật sản phẩm thất bại!")
			console.log("Error update product", error)
		});
	};
	
	//Xóa sản phẩm
	$scope.delete = function(item){
		if($scope.form.id !=null){
			if(confirm("Bạn có chắc xóa sản phẩm này?")){
				var item = angular.copy($scope.form)
				$http.delete(`/rest/products/${item.id}`).then(resp =>{
					if(resp.data) {
						var index = $scope.items.findIndex(p =>p.id == item.id)
						$scope.items.splice(index,1)
						$scope.reset()
						alert("Xóa sản phẩm thành công!")
					} else {
						alert("Sản phẩm đã được đặt mua, không thể xóa!")
					}
					console.log("Success progessing delete product")
				}).catch(error =>{
					alert("Xóat sản phẩm thất bại!")
					console.log("Error delete product", error)
				});
			}
		}
		else{
			$window.scrollTo(0, 0);
			$scope.isError=true;
		}
	};
	
	//submit
	$scope.SaveOrUpdate = function(){
		if($scope.isUpdate){ //isUpdate = true thì update, ngược lại create
			$scope.update()
		}
		else{
			$scope.create()
		}
	}
	
	//set submit là update
	$scope.setUpdate = function(){
		if($scope.form.id != null){ //Đã chọn sp
			$scope.isUpdate = true;
		}
		else{
			$window.scrollTo(0, 0);
			$scope.isError=true;
		}
	}
	
	//set submit là create
	$scope.setCreate = function(){
		$scope.isUpdate = false;
	}
	
	//Upload ảnh image
	$scope.chooseFile = function (files){
		var data = new FormData();
		data.append('file',files[0]);
		$http.post(`/rest/upload/images`, data,{
			transformRequest: angular.identity,
			headers: {'Content-Type':undefined}
		}).then(resp =>{
			$scope.form.image = resp.data.name
		}).catch(error =>{
				alert("Lỗi upload ảnh")
				console.log("Error upload", error)
		})
	};
	
	//Upload ảnh image2
	$scope.chooseFile2 = function (files){
		var data = new FormData();
		data.append('file',files[0]);
		$http.post(`/rest/upload/images`, data,{
			transformRequest: angular.identity,
			headers: {'Content-Type':undefined}
		}).then(resp =>{
			$scope.form.image2 = resp.data.name
		}).catch(error =>{
				alert("Lỗi upload ảnh")
				console.log("Error upload", error)
		})
	};
	
	//Upload ảnh image3
	$scope.chooseFile3 = function (files){
		var data = new FormData();
		data.append('file',files[0]);
		$http.post(`/rest/upload/images`, data,{
			transformRequest: angular.identity,
			headers: {'Content-Type':undefined}
		}).then(resp =>{
			$scope.form.image3 = resp.data.name
		}).catch(error =>{
				alert("Lỗi upload ảnh")
				console.log("Error upload", error)
		})
	};
	
	//Phân trang
	$scope.pager = {
		page: 0,
		size: 5,
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
	
});






