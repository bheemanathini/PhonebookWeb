(function(){
	'use strict';
	angular.module('Phonebook')
	.controller('AddMembersToGroupController', ['groupInfo', 'ContactsService','GroupsService', '$scope',function(groupInfo, ContactsService, GroupsService, $scope){
		var addMembersToGroupCtrl = this;
		addMembersToGroupCtrl.groupInfo = groupInfo;
		var response = ContactsService.getAllContacts();
		
		response.then(function(response){
			var contacts = response.data;
			var members = groupInfo.members;
			var contactsWithMembershipInfo = [];
			for(var i =0; i < contacts.length; i++){
				var member = hasMembership(contacts[i], members);
				var contactWithMemberShipInfo = {
						isMember: member,
						contact: contacts[i],
						groupId: addMembersToGroupCtrl.groupInfo.id				    
				};
				contactsWithMembershipInfo.push(contactWithMemberShipInfo);
				
			}
			addMembersToGroupCtrl.contactsWithMembershipInfo = contactsWithMembershipInfo;

		});

		function hasMembership(contact, members){
			for(var i = 0; i < members.length; i++){
				if(members[i].id === contact.id)
					return true;
			}
			
			return false;
		}
		addMembersToGroupCtrl.modify = function(){
			console.log("Smodify called");
			var cids = [];
			for(var i = 0; i < addMembersToGroupCtrl.contactsWithMembershipInfo.length; i++){
				if(addMembersToGroupCtrl.contactsWithMembershipInfo[i].isMember){
					cids.push(addMembersToGroupCtrl.contactsWithMembershipInfo[i].contact.id);
				}
			}
			console.log("//", addMembersToGroupCtrl.groupInfo.id);
			GroupsService.addContactToGroup(cids, addMembersToGroupCtrl.groupInfo.id);
		}
		
		
	}]);



})();