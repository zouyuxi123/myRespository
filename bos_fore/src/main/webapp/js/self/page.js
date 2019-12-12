function ctrlRead($scope, $http ) {
	$scope.currentPage = 1;
	$scope.pageSize = 4;
	$scope.totalCount = 0;
	$scope.totalPages = 0;

	$scope.prev = function() {
		if($scope.currentPage > 1) {
			$scope.selectPage($scope.currentPage - 1);
		}
	}

	$scope.next = function() {
		if($scope.currentPage < $scope.totalPages) {
			$scope.selectPage($scope.currentPage + 1);
		}
	}

	$scope.selectPage = function(page) {
		$http({
			method: 'GET',
			url: 'data/promotion'+page+'.json',
			params: {
				"page": page,
				"pageSize": $scope.pageSize
			}
		}).success(function(data, status, headers, config) {
			$scope.pageItems = data.pageData;
			$scope.totalCount = data.totalCount;
			$scope.totalPages = Math.ceil($scope.totalCount / $scope.pageSize);

			// 第1,2 页 
			if(page <= 2) {
				$scope.pageList = [];
				$scope.newPages = $scope.totalPages > 5 ? 5 : $scope.totalPages;
				for(var i = 0; i < $scope.newPages; i++) {
					$scope.pageList.push(i + 1);
				}
			}
		}).error(function(data, status, headers, config) {
			// 当响应以错误状态返回时调用
		});

		//不能小于1大于最大
		if(page < 1 || page > $scope.totalPages) return;

		//最多显示分页数5
		if(page > 2) {
			//因为只显示5个页数，大于2页开始分页转换
			var newpageList = [];
			for(var i = (page - 3); i < ((page + 2) > $scope.totalPages ? $scope.totalPages : (page + 2)); i++) {
				newpageList.push(i + 1);
			}
			$scope.pageList = newpageList;
		}
		$scope.currentPage = page;
		$scope.isActivePage(page);
	}

	$scope.isActivePage = function(page) {
		return page == $scope.currentPage;
	}

	// 发起请求 显示第一页数据 
	$scope.selectPage($scope.currentPage);
	
}
ctrlRead.$inject = ['$scope', '$http' ];