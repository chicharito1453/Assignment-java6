app.controller("authority-ctrl",function($scope,$http,$location){
	$scope.roles = [];
	$scope.admins = [];
	$scope.authorities = [];
	$scope.isUnauthorized = true;
	
	$scope.initialize = function(){
		//lấy tất cả vai trò
		$http.get(`/rest/roles`).then(resp =>{
			$scope.roles = resp.data;
			console.log("Success get roles")
		}).catch(error =>{
			console.log("Error get roles",error)
		});
		
		//lấy staff và admin
		$http.get(`/rest/accounts?admin=true`).then(resp =>{
			$scope.admins = resp.data;
			console.log("Success get administrators")
		}).catch(error =>{
			console.log("Error get administrators",error)
		});
		
		//lấy các quyền của administrtors
		$http.get(`/rest/authorities?admin=true`).then(resp =>{
			$scope.authorities = resp.data;
			console.log("Success get authorities",resp.data)
		}).catch(error =>{
			console.log("Error get authorities",error)
		});
	}
	
	//checked input quyền của account
	$scope.authority_of = function(acc, role){
		if($scope.authorities && $scope.isUnauthorized){
			return $scope.authorities.find(auth => auth.account.username == acc.username && auth.role.id == role.id);
		}
	};
	
	//phân quyền
	$scope.authority_changed = function(acc, role){
		var authority = $scope.authority_of(acc, role)
		if(authority){//đã cấp quyền => thu hồi
			$scope.revoke_authority(authority)
		}
		else{//chưa có quyền này => cấp quyền
			authority = {account:acc, role:role}
			$scope.grand_authority(authority)
		}
	};
	
	//thu hồi quyền
	$scope.revoke_authority = function(authority){
		$http.delete(`/rest/authorities/${authority.id}`).then(resp=>{
			var index = $scope.authorities.findIndex(auth => auth.id == authority.id);
			$scope.authorities.splice(index, 1)
			alert("Thu hồi quyền thành công!")
		}).catch(error =>{
			alert("Thu hồi quyền thất bại!")
			console.log("Error revoke",error)
		})
	};
	
	//cấp quyền
	$scope.grand_authority = function(authority){
		$http.post(`/rest/authorities`, authority).then(resp=>{
			$scope.authorities.push(resp.data)
			alert("Cấp quyền thành công!")
		}).catch(error =>{
			alert("Cấp quyền thất bại!")
			console.log("Error grand",error)
		})
	}
	
	$scope.isUnauthorized = function(){
		$http.get(`/rest/unauthorized`).then(resp=>{
			$scope.isUnauthorized = resp.data;
		}).catch(error =>{
			console.log("Test",error)
		});
	}
	
	$scope.isUnauthorized();
	$scope.initialize();
	
})