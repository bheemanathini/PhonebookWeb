(function(){
	'use strict';
	angular.module('Phonebook')
	.service('GroupsService', GroupsService);

	GroupsService.$inject = ['$http', '$state']
	function GroupsService($http, $state){
		var service = this;
		service.getAllGroups = function(){
			var response = $http({
				method: "GET",
				url: ("http://localhost:8080/Phone/webapi/groups")
			});

			return response;
		};

		service.getGroupInfo = function(id){
			var response = $http({
				method: "GET",
				url: ("http://localhost:8080/Phone/webapi/groups/"+id)
			});

			return response;
		};

		service.addContactToGroup = function(cid, gid){
			var cids;
			var param = cid.join();
			$http({
				url: 'http://localhost:8080/Phone/webapi/groups/'+gid+"/setMembers", 
				method: "GET",
				params: {cid: param}
			}).then(function(data){
				var url = 'viewgroup'
					$state.go(url, {id:gid}, {reload:true})

			});


		}

		service.deleteGroup = function(id){
			$http.delete('http://localhost:8080/Phone/webapi/groups/'+id)
			.then(function(data){
				var url = 'groups';
				$state.go(url, {}, {reload:true})

			});
		};

		service.addGroup = function(group){
			
			var parameter = JSON.stringify({
				name: group.groupname
			});
			$http.post('http://localhost:8080/Phone/webapi/groups/addGroup', parameter)
			.then(function(data){
				var url = 'groups';
				$state.go(url)
			});
		};

	}


})();