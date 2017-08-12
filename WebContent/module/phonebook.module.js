(function(){

	'use strict';
	angular.module('Phonebook', ['ui.router'])
	.config(RoutesConfig);

	RoutesConfig.inject= ['$stateProvider', '$urlRouterProvider', '$httpProvider'];

	function RoutesConfig($stateProvider, $urlRouterProvider, $httpProvider){
		
		 $httpProvider.interceptors.push(['$q', '$location', '$window', function ($q, $location, $window) {
		        return {
		            'responseError': function(response) {
		            	console.log("i am in the ",response.status);
		                if(response.status === 404 || response.status === 400) {
                           $window.location.assign('/Phone/error.html');
		                   // $location.url('/error.html'); 
		                   // $location.replcae();// Replace with whatever should happen
		                }
		                return $q.reject(response);
		            }
		        };
		    }]);

		$stateProvider
		
		.state('error', {
			url: '/error',
			templateUrl : 'error.html'
		})

		.state('groups', {
			url:'/groups',
			templateUrl: 'view/groups.html',
			controller: 'GroupsListController as groupsList',
			resolve: {
				groups: ['GroupsService', function(GroupsService){
					return GroupsService.getAllGroups()
					.then(function(response){
						return response.data;
					});
				}]
			}
		})
		.state('addMembers', {
			url:'/groups/addMembers/{id}',
			templateUrl: 'view/addMembersToGroup.html',
			controller: 'AddMembersToGroupController as addMembersToGroupCtrl',
			resolve: {
				groupInfo: ['$stateParams', 'GroupsService',
					function ($stateParams, GroupsService) {
					return GroupsService.getGroupInfo($stateParams.id)
					.then(function (response) {
						return response.data;
					});
				}]
			}
		})


		.state('contacts', {
			url: '/contacts',
			templateUrl: 'view/contacts.html',
			controller: 'ContactsListController as contactsList',
			resolve:{
				contacts: ['ContactsService', function(ContactsService){
					return ContactsService.getAllContacts()
					.then(function(response){
						return response.data;
					})
					/*.catch(function (error) {
						console.log("Something went terribly wrong.");
					});;*/
				}]
			}
		})

		.state('addContact', {
			url:'/addContact',
			templateUrl: 'view/addContact.html',
			controller: 'AddContactController as addContactCtrl'
		})

		.state('addGroup', {
			url:'/addGroup',
			templateUrl: 'view/addGroup.html',
			controller: 'AddGroupController as addGroupCtrl'
		})
		.state('viewgroup', {
			url:'/groups/{id}',
			templateUrl: 'view/viewgroup.html',
			controller: 'ViewGroupController as viewGroupCtrl',
			resolve: {
				groupInfo: ['$stateParams', 'GroupsService',
					function ($stateParams, GroupsService) {
					return GroupsService.getGroupInfo($stateParams.id)
					.then(function (response) {
						console.log("view group",response.data);
						return response.data;
					});
				}]
			}
		});




		/*.state('students', {
			url:'/students',
			templateUrl:'students.html'
		});
		 */

	}

	/*	resolve:{
		contacts: ['GroupsService', function(GroupsService){
			return GroupsService.getAllGroups()
			.then(function(response){
				return response.data;
			});
		}]
	}*/
})();