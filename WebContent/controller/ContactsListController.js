(function(){

	'use strict';
	angular.module('Phonebook')
	.controller('ContactsListController', ContactsListController);

	ContactsListController.$inject = ['contacts', '$http', '$state', 'ContactsService']
	function ContactsListController(contacts, $http, $state, ContactsService){
		var contactsList = this;
		contactsList.contacts = contacts;

		contactsList.removeContact = function(id){
			console.log("remove called", id);
			ContactsService.deleteContact(id);
		}

	}



})();