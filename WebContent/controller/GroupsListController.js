(function(){

	'use strict';
	angular.module('Phonebook')
	.controller('GroupsListController', GroupsListController);

	GroupsListController.$inject = ['groups', '$http', '$state', 'GroupsService']
	function GroupsListController(groups, $http, $state, GroupsService){
		var groupsList = this;
		groupsList.groups = groups;
		
		groupsList.removeGroup = function(id){
			GroupsService.deleteGroup(id);
			
		}
	}

})();