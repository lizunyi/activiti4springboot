/*
 * Activiti Modeler component part of the Activiti project
 * Copyright 2005-2014 Alfresco Software, Ltd. All rights reserved.
 * 
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.

 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 */

/*
 * Form Properties
 */

var KisBpmFormPropertiesCtrl = [ '$scope', '$modal', '$timeout', '$translate', function($scope, $modal, $timeout, $translate) {

    // Config for the modal window
    var opts = {
        template:  'editor-app/configuration/properties/form-properties-popup.html?version=' + Date.now(),
        scope: $scope
    };

    // Open the dialog
    $modal(opts);
}];

var KisBpmFormPropertiesPopupCtrl = ['$scope', '$q', '$translate', '$timeout', function($scope, $q, $translate, $timeout) {
    // Put json representing form properties on scope
	var $meta = $scope.editor.modelMetaData;
	var modelId = $meta.modelId;
	if(!$meta.$formProperties){
		jQuery.ajax({
			url: '/workflow/query/properties/'+modelId,
			async:false,
			success:function(data){
				$meta.$formProperties = data;
			}
		});
	}
    var $metaCopy = angular.copy($meta.$formProperties);
	if ($scope.property.value !== undefined && $scope.property.value !== null
        && $scope.property.value.formProperties !== undefined
        && $scope.property.value.formProperties !== null) {
        // Note that we clone the json object rather then setting it directly,
        // this to cope with the fact that the user can click the cancel button and no changes should have happended
        var $formProperties = angular.copy($scope.property.value.formProperties);
        for(var $x = 0 ; $x < $formProperties.length; $x++){
        	var $p = $formProperties[$x];
        	for(var $y = 0 ; $y < $metaCopy.length; $y++){
        		var $f = $metaCopy[$y];
        		if($p.name == $f.name){
        			$f["required"] = $p["required"];
        			$f["readonly"] = $p["readonly"];
        			$f["hidden"] = $p["hidden"];
        			break;
        		}
        	}
        }
    }
	$scope.formProperties = $metaCopy;

    // Array to contain selected properties (yes - we only can select one, but ng-grid isn't smart enough)
    $scope.selectedProperties = [];
    $scope.selectedEnumValues = [];
    
    $scope.translationsRetrieved = false;
    
    $scope.labels = {};
    
    var idPromise = $translate('PROPERTY.FORMPROPERTIES.ID');
    var namePromise = $translate('PROPERTY.FORMPROPERTIES.NAME');
    var typePromise = $translate('PROPERTY.FORMPROPERTIES.TYPE');


    $scope.propCheck = function(row,propIndex){
    	var props = ["required","readonly","hidden"];
    	for(var i = 0 ;i < 3;i++){
    		row.entity[props[i]] = propIndex == i;
    	}
    }
    
    $q.all([idPromise, namePromise, typePromise]).then(function(results) { 
    	$scope.labels.idLabel = results[0];
        $scope.labels.nameLabel = results[1];
        $scope.labels.typeLabel = results[2];
        $scope.translationsRetrieved = true;
        
    	// Config for grid
        $scope.gridOptions = {
            data: 'formProperties',
            enableRowReordering: false,
            headerRowHeight: 28,
            multiSelect: false,
            keepLastSelected : false,
            selectedItems: $scope.selectedProperties,
            columnDefs: [
//            	{ field: 'id', displayName: $scope.labels.idLabel },
                { field: 'name', displayName: $scope.labels.nameLabel},
                { field: 'required', displayName: "是否必填",
                	cellTemplate : '<div class="ngCellText" style="padding:8px;"><input ng-click="propCheck(row,0)" ng-checked="row.entity.required" type="checkbox" /></div>'
                },
                { field: 'readonly', displayName: "是否只读",
                	cellTemplate : '<div class="ngCellText" style="padding:8px;"><input ng-click="propCheck(row,1)" ng-checked="row.entity.readonly" type="checkbox" /></div>'
                },
                { field: 'hidden', displayName: "是否隐藏",
                    cellTemplate : '<div class="ngCellText" style="padding:8px;"><input ng-click="propCheck(row,2)" ng-checked="row.entity.hidden" type="checkbox" /></div>'
                }
            ]
        };
    });
    // Click handler for save button
    $scope.save = function() {

        if ($scope.formProperties.length > 0) {
            $scope.property.value = {};
            $scope.property.value.formProperties = $scope.formProperties;
        } else {
            $scope.property.value = "";
        }

        $scope.updatePropertyInModel($scope.property);
        $scope.close();
    };

    $scope.clear = function(){
    	$scope.formProperties = "";
    };
    
    $scope.cancel = function() {
    	$scope.$hide();
    	$scope.property.mode = 'read';
    };

    // Close button handler
    $scope.close = function() {
    	$scope.$hide();
    	$scope.property.mode = 'read';
    };

}];