(function(){
	'use strict';
	angular.module('Phonebook')
	.service('ContactsService', ContactsService);

	ContactsService.$inject = ['$http', '$state']
	function ContactsService($http, $state){
		var service = this;
		service.getAllContacts = function(){

			console.log("contacts service called");
			var response = $http({
				method: "GET",
				url: ("http://localhost:8080/Phone/webapi/contacts")
			});

			return response;
		};
		service.deleteContact = function(id){
            console.log("delete called", id)
			$http.delete('http://localhost:8080/Phone/webapi/contacts/'+id)
			.then(function(data){
				var url = 'contacts';
				$state.go(url, {}, {reload:true})

			});
		};
		
		service.addContact = function(contact){
			$http({
				
				method:'POST',
				url: 'http://localhost:8080/Phone/webapi/contacts/add',
				headers: {'Content-Type': 'application/x-www-form-urlencoded'},
				transformRequest: function(obj){
					var str = [];
					for(var p in obj)
						str.push(encodeURIComponent(p) + "=" + encodeURIComponent(obj[p]));
					    return str.join("&");
				},
				data: contact
			
				}).then(function(data){
					var url = 'contacts';
					$state.go(url)
				});
		};

	}


})();