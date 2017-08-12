(function(){
	'use strict';
	angular.module('Phonebook')
	.controller('AddGroupController', AddGroupController);
	
	AddGroupController.$inject = ['$http', '$state', 'GroupsService']
	function AddGroupController($http, $state, GroupsService){
		var addGroupCtrl = this;
		
		addGroupCtrl.group = {};
		addGroupCtrl.submit = function(){
			GroupsService.addGroup(addGroupCtrl.group);
		};
	}
})();