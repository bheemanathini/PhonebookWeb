(function(){
	'use strict';
	angular.module('Phonebook')
	.controller('ViewGroupController', ViewGroupController);
	
	ViewGroupController.$inject = ['groupInfo']
	function ViewGroupController(groupInfo){
		var viewGroupCtrl = this;
		viewGroupCtrl.groupInfo = groupInfo;
		
	}
})();