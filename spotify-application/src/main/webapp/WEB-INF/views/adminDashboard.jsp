<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<title>Admin dashboard</title>
		<link type="text/css" rel="stylesheet" href="webjars/bootstrap/4.1.1/dist/css/bootstrap.min.css">
		<link type="text/css" rel="stylesheet" href="stylesheets/popup.css">
		<link type="text/css" rel="stylesheet" href="webjars/datatables/1.10.16/css/dataTables.bootstrap4.min.css">
		<script type="text/javascript" src="webjars/bootstrap/4.1.1/dist/js/bootstrap.min.js"></script>
		<script type="text/javascript" src="webjars/jquery/3.1.1/jquery.min.js"></script>
		<script type="text/javascript" src="webjars/popper.js/1.14.3/dist/umd/popper.min.js"></script>
		<script type="text/javascript" src="webjars/datatables/1.10.16/js/dataTables.bootstrap4.min.js"></script>
		<script type="text/javascript" src="webjars/datatables/1.10.16/js/jquery.dataTables.min.js"></script>
		<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.0.13/css/all.css" integrity="sha384-DNOHZ68U8hZfKXOrtjWvjxusGo9WQnrNx2sqG0tfsghAvtVlRW3tvkXWZh58N9jp" crossorigin="anonymous">
		<style>
			* {
			  box-sizing: border-box;
			}

			#myInput {
			  background-image: url('/css/searchicon.png');
			  background-position: 10px 10px;
			  background-repeat: no-repeat;
			  width: 100%;
			  font-size: 16px;
			  padding: 12px 20px 12px 40px;
			  border: 1px solid #ddd;
			  margin-bottom: 12px;
			}
			
			#myTable {
			  border-collapse: collapse;
			  width: 100%;
			  border: 1px solid #ddd;
			  font-size: 18px;
			}
			
			#myTable th, #myTable td {
			  text-align: left;
			  padding: 12px;
			}
			
			#myTable tr {
			  border-bottom: 1px solid #ddd;
			}
			
			#myTable tr.header, #myTable tr:hover {
			  background-color: #f1f1f1;
			}
		
		</style>
		
		<style type="text/css">
	        .table {
	            width:  100%;
	            border-collapse: collapse;
	        }
	        .table td {
   				text-align: center;   
			}
			.table th {
   				text-align: center;   
			}
	        .scrollingTable {
	            width: 100%;
	            overflow-y: auto;
	        }
	        .modal-body {
	        	width: 100%;
	        	height: 350px;
	        	overflow-y: auto;
	        }
	        select.form-control:not([size]):not([multiple]) {
				height: calc(2.25rem + 6px);
			}
    	</style>
	</head>
	<body class="mockaroo">
		<nav class="navbar navbar-expand-lg navbar-dark bg-dark navbar-fixed-top">
	      <div class="collapse navbar-collapse justify-content-md-center">
	        <ul class="navbar-nav">
	          <li class="nav-item active">
	            <a class="navbar-brand" href="/adminDashboard.do"><i class="fa fa-home"></i>Home</a>
	          </li>
	          <li class="nav-item">
	            <a class="nav-link" href="#">ID3 Generator</a>
	          </li>
	          <li class="nav-item">
	            <a class="nav-link" href="#">Multidimensional DB Generator</a>
	          </li>
	        </ul>
	      </div>
	    </nav>
		<div class="breadcrumbs-bar-spacer"></div>
		<div class="container-fluid">
			<!--div class="modal fade in" id="advanced_formula_dialog" style="display: block;" aria-hidden="false"-->
			<div class="modal fade" id="advanced_formula_dialog" style="display: none;" aria-hidden="true">
				<div class="modal-dialog">
					<div class="modal-content">
					<h3 class="modal-title">Formula</h3>
						<div class="modal-header">
							<div class="pull-right"></div>
							
						</div>
						<div class="modal-body">
							<p>Please define the expression rule to group your attribute values:
								<p>Example:</p>
									<p>
										<code>Insert the number of expressions: '3'</code>
										=&gt; define the number of expressions to 3
									</p>
									<p>
										<code>Select one of the following conditions: '>='</code>
										=&gt; define condition operator to '>='
									</p>
									<p>
										<code>Insert the value to compare: '20000'</code>
										=&gt; The input value will be compared through the operator ('i.e: this.attrValue '>=' 20000')
									</p>
									<p>
										<code>Define the attribute's new value: 'High Salary'</code>
										=&gt; If the value match the condition, this will be the new value ('i.e: this.attrValue = High Salary');
									</p>
									<br>
									<p>The same process will be repeated for the next 2 expressions</p>
							<hr>
							<form>
								<div class="form-group">
							    	<label for="expressions">Insert the number of expressions</label>
							    	<input type="number" class="form-control" id="expressions" placeholder="Number of expressions">
							  	</div>
							</form>
							<form id="exprForm">
							</form>
						</div>
						<div class="modal-footer">
							<button id="acceptButton" class="btn btn-primary apply" style="margin-right: 15px" type="button" onclick="applyFormula()">Apply</button>
							<button class="btn btn-warning apply" style="margin-right: 15px" type="button" onclick="dismiss()">Close</button>
						</div>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-xl-2 col-md-2 col-sm-2"></div>
				<div class="col-xl-8 col-md-8 col-sm-8">
					<h1>Welcome Admin: ${userId}</h1>
				</div>
				<div class="col-xl-2 col-md-2 col-sm-2"></div>
			</div>
			<br></br>
			<div class="row">
				<div class="col-xl-2 col-md-2 col-sm-2"></div>
				<div class="col-xl-4 col-md-4 col-sm-4">
					<p>Please select a database</p>
					<form action="">
						<select id="dbSelect" name="databases" onclick="showDatabases()" onchange="enableTables(this.value)">
						<option value="" class="text-muted">Select a database:</option>
						</select>
					</form>
				</div>
				<div class="col-xl-4 col-md-4 col-sm-4">
					<p>Please select a table</p>
					<form action="">
						<select disabled="true" id="tableSelect" name="tables" onclick="showTables()" onchange="displayMetadata(this.value)">
						<option value="" class="text-muted">Select a table:</option>
						</select>
					</form>
				</div>
				<div class="col-xl-2 col-md-2 col-sm-2"></div>
			</div>
			<br></br>
			<div class="row">
				<div class="col-xl-2 col-md-2 col-sm-2"></div>
				<div class="col-xl-8 col-md-8 col-sm-8">
					<input disabled="true" type="text" id="myInput" onkeyup="search(metaTable)" placeholder="Search for names.." title="Type in a name">
				</div>
				<div class="col-xl-2 col-md-2 col-sm-2"></div>
			</div>
			<br></br>
			<div class="row">
				<div class="col-xl-1 col-md-1 col-sm-1"></div>
				<div class="col-xl-10 col-md-10 col-sm-10">
					<div class="row">
						<div class="scrollingTable" id="tableMetadata">
							<table id="metaTable" class="table table-striped table-bordered dataTable" style="width: 100%;" role="grid" aria-describedby="metadata_info"></table>
						</div>
					</div>
				</div>
				<div class="col-xl-1 col-md-1 col-sm-1"></div>
			</div>
			<br></br>
			<div class="row">
				<div class="col-xl-1 col-md-1 col-sm-1"></div>
				<div class="col-xl-10 col-md-10 col-sm-10">
					<div class="row">
						<div class="scrollingTable" id="tableAttributes">
							<table id="attrTable" class="table table-striped table-bordered dataTable" style="width: 100%;" role="grid" aria-describedby="attribute_info"></table>
						</div>
					</div>
				</div>
				<div class="col-xl-1 col-md-1 col-sm-1"></div>
			</div>
			<div class="row">
				<div class="col-xl-1 col-md-1 col-sm-1"></div>
				<div class="col-xl-10 col-md-10 col-sm-10">
					<div class="row">
						<div class="scrollingTable" id="previewsTable">
							<table id="previewTable" class="table table-striped table-bordered dataTable" style="width: 100%;" role="grid" aria-describedby="preview_info"></table>
						</div>
					</div>
				</div>
				<div class="col-xl-1 col-md-1 col-sm-1"></div>
			</div>
			<div class="row">
				<div class="col-xl-8 col-md-8 col-sm-8"></div>
				<div class="col-xl-2 col-md-2 col-sm-2">
				</div>
				<div class="col-xl-2 col-md-2 col-sm-2">
					<button id="submitButton" class="btn btn-success apply" style="display: none;" onclick="submitTable()" disabled="true">submit</button>
				</div>
			</div>
		</div>
		
		<script>
			function showDatabases() {
				var dbSelect = document.getElementById("dbSelect");
				var length = dbSelect.children.length;
				//var dbOption = dbSelect.getElemenstByTagName("option");
				
				console.log(length)
				if(length == 1){
					var xhttp;
					xhttp = new XMLHttpRequest();
					xhttp.onreadystatechange = function(){
						if(this.readyState == 4 && this.status == 200){
							var databases = JSON.parse(this.responseText);
							console.log(databases);
							for(var i in databases){
								var option = document.createElement("option");
								console.log(databases[i]);
								option.text = databases[i];
								option.value = databases[i];
								dbSelect.add(option);
							}
						}
					};
					xhttp.open("GET", "/GetDatabasesServlet", true);
					xhttp.send();	
				}
			}
			
			function enableTables(str){
				if(str==""){
					console.log(str);
					document.getElementById("tableSelect").disabled = true;
					document.getElementById("dbSelect").innerHTML = "<option value=\'\'>Select a database:</option>";
					resetTable();
				}
				else{
					console.log(str);
					document.getElementById("tableSelect").disabled = false;
					resetTable();
				}
			}
			
			function resetTable(){
				document.getElementById("metaTable").innerHTML = "";
				document.getElementById("attrTable").innerHTML = "";
				document.getElementById("tableSelect").innerHTML = "<option value=\'\'>Select a table:</option>"
			}
			
			function showTables(){
				var	dbSelect = document.getElementById("dbSelect");
				var tableSelect = document.getElementById("tableSelect");
				var currentSelectedDatabase = dbSelect.options[dbSelect.selectedIndex].value;	
				var length = tableSelect.children.length;
				if(length == 1){
					var xhttp;
					xhttp = new XMLHttpRequest();
					xhttp.onreadystatechange = function(){
						if(this.readyState == 4 && this.status == 200){
							var tables = JSON.parse(this.responseText);
							console.log(tables);
							for(var i in tables){
								var option = document.createElement("option");
								option.text = tables[i];
								option.value = tables[i];
								tableSelect.add(option);
							}
						}
					};
					xhttp.open("GET", "/GetTablesServlet?dbName=" + currentSelectedDatabase, true);
					xhttp.send();	
				}
			}
			
			function displayMetadata(str){
				if(str = ""){
					document.getElementById("metaTable").innerHTML = "";
				}
				else{
					document.getElementById("metaTable").innerHTML = "";
					var	dbSelect = document.getElementById("dbSelect");
					var tableSelect = document.getElementById("tableSelect");
					var currentSelectedDatabase = dbSelect.options[dbSelect.selectedIndex].value;
					var currentSelectedTable = tableSelect.options[tableSelect.selectedIndex].value;
					var xhttp;
					xhttp = new XMLHttpRequest();
					xhttp.onreadystatechange = function(){
						if(this.readyState == 4 && this.status == 200){
							var tableContent = JSON.parse(this.responseText);
							var tableResponse = "";
							
							var tab, thead, tbody, tr, th, td, tn, a, button, i;
							var row, column;
							tab = document.getElementById("metaTable");
							var flag = true;
							
							for (row = 0; row < tableContent.length; row++){
								tr = document.createElement("tr");
								tr.id = "rowId_" + row;
								
								for(column=0; column < tableContent[row].length; column++){
									if(flag == true){
										th = document.createElement("th");
										th.setAttribute("scope","column");
										tn = document.createTextNode(tableContent[row][column]);
										th.appendChild(tn);
										tr.appendChild(th);
									}
									else if(column == 0){
										th = document.createElement("th");
										th.setAttribute("scope","row");
										tn = document.createTextNode(tableContent[row][column]);
										th.appendChild(tn);
										tr.appendChild(th)
									}
									else{
										td = document.createElement("td");
										tn = document.createTextNode(tableContent[row][column]);
										td.appendChild(tn);
										tr.appendChild(td);
									}
								}
								
								
								if(row == 0){
									th = document.createElement("th");
									tn = document.createTextNode("");
									th.appendChild(tn);
									tr.appendChild(th);
									
									thead = document.createElement("thead");
									thead.setAttribute("class","thead-dark");
									tbody = document.createElement("tbody");
									
									thead.appendChild(tr);
									flag = false;
								}
								else{
									td =  document.createElement("td");
									i = document.createElement("i");
									i.setAttribute("class", "fas fa-plus");
									
									button = document.createElement("button");
				    				button.setAttribute("type", "button");
				    				button.setAttribute("class", "btn btn-success");
				    				button.setAttribute("onclick", "addAttribute(" + row +")");
				    				button.appendChild(i);
									
									td.appendChild(button);
									tr.appendChild(td);
									tbody.appendChild(tr);
								}
							}
							
							tab.appendChild(thead);
							tab.appendChild(tbody);
							makeTableScroll("metaTable");
						}
					};
					xhttp.open("GET", "/GetTableContentServlet?dbName=" + currentSelectedDatabase + "&tableName=" + currentSelectedTable, true);
					xhttp.send();
				}
			}
		</script>
		
		<script>
			function search(tableId) {
			  console.log(tableId);
			  var input, filter, table, tr, th, i;
			  input = document.getElementById("myInput");
			  filter = input.value.toUpperCase();
			  table = tableId;
			  tr = table.getElementsByTagName("tr");
			  for (i = 1; i < tr.length; i++) {
			    th = tr[i].getElementsByTagName("th")[0];
			    if (th) {
			      if (th.innerHTML.toUpperCase().indexOf(filter) > -1) {
			        tr[i].style.display = "";
			      } else {
			        tr[i].style.display = "none";
			      }
			    }       
			  }
			}
		</script>
		
		<script type="text/javascript">
        	function makeTableScroll(tableId) {
            	// Constant retrieved from server-side via JSP
            	var maxRows = 8;
            	var table = document.getElementById(tableId);
            	var wrapper = table.parentNode;
            	var rowsInTable = table.rows.length;
            	var height = 0;
            	if (rowsInTable > maxRows) {
                for (var i = 0; i < maxRows; i++) {
                    height += table.rows[i].clientHeight;
                }
                wrapper.style.height = height + "px";
            }
        }
    	</script>
    	
    	<script type="text/javascript">
	    	function deleteRow(Id)  {
	    		console.log(Id.innerHTML);
	    	    var row = Id;//document.getElementById(Id);
	    	    var table = row.parentNode;
	    	    while ( table && table.tagName != 'TABLE' )
	    	        table = table.parentNode;
	    	    if ( !table )
	    	        return;
	    	    
	    	    var button, checked;
	    	    button = document.getElementById("submitButton");
	    	    checked = row.children[5].firstChild;
	    	    console.log(checked);
	    	    
	    	    if(verifyChecked(checked)){
	    	    	button.setAttribute("disabled",true);	
	    	    }
	    	    
	    	    table.deleteRow(row.rowIndex);
	    	    var tbody = table.children[1];
	    	    
	    	    if(tbody.children.length == 0){
	    	    	table.innerHTML = "";
	    	    	button.setAttribute("style","display: none;");
	    	    	button.setAttribute("disabled",true);
	    	    }
	    	}

    		function addAttribute(row) {
    			var table, thead, tbody, th, tr, tn, selectedRow, dbSelect, tableSelect;
    			table = document.getElementById("attrTable");
    			
    			if(table.children.length == 0){
    				var button = document.getElementById("submitButton");
	    	    	button.setAttribute("style","display: block;");
	    	    	button.setAttribute("disabled",false);
	    	    	
    				tr = document.createElement("tr");
    				th = document.createElement("th");
    				th.setAttribute("scope","column");
					tn = document.createTextNode("Database");
					th.appendChild(tn);
					tr.appendChild(th);
					
					th = document.createElement("th");
    				th.setAttribute("scope","column");
					tn = document.createTextNode("Relation");
					th.appendChild(tn);
					tr.appendChild(th);
					
					th = document.createElement("th");
    				th.setAttribute("scope","column");
					tn = document.createTextNode("Attribute");
					th.appendChild(tn);
					tr.appendChild(th);
					
					th = document.createElement("th");
    				th.setAttribute("scope","column");
					tn = document.createTextNode("Data Type");
					th.appendChild(tn);
					tr.appendChild(th);
					
					th = document.createElement("th");
    				th.setAttribute("scope","column");
					tn = document.createTextNode("Delete");
					th.appendChild(tn);
					tr.appendChild(th);
					
					th = document.createElement("th");
    				th.setAttribute("scope","column");
					tn = document.createTextNode("Decision");
					th.appendChild(tn);
					tr.appendChild(th);
					
					th = document.createElement("th");
    				th.setAttribute("scope","column");
					tn = document.createTextNode("F(x)");
					th.appendChild(tn);
					tr.appendChild(th);
					
					thead = document.createElement("thead");
    				thead.setAttribute("class","thead-dark");
    				thead.appendChild(tr);
    				tbody = document.createElement("tbody");
					
    				table.appendChild(thead);
    				table.appendChild(tbody);
    				makeTableScroll("attrTable");
    			}
    			
    			tbody = table.children[1];
    			selectedRow = document.getElementById("rowId_" + row);
    			
    			var parameters = [];
    			var dbName, relationName, attributeName, dataType;
    			
    			dbSelect = document.getElementById("dbSelect");
    			dbName = dbSelect.options[dbSelect.selectedIndex].value;
    			
    			tableSelect = document.getElementById("tableSelect");
    			relationName = tableSelect.options[tableSelect.selectedIndex].value;
    			
    			attributeName = selectedRow.children[0].innerHTML;
    			dataType = selectedRow.childNodes[1].innerHTML;
    			
    			parameters.push(dbName);
    			parameters.push(relationName);
    			parameters.push(attributeName);
    			tr = document.createElement("tr");
    			rowId = dbName + relationName + attributeName;
    			tr.id = rowId;
    			
    			if(checkDuplicity(parameters, "attrTable") == false){
    				var button, i, label, div, p;
    				
    				td =  document.createElement("td");
    				tn = document.createTextNode(dbName);
    				td.appendChild(tn);
    				tr.appendChild(td);
    		
    				td =  document.createElement("td");
    				tn = document.createTextNode(relationName);
    				td.appendChild(tn);
    				tr.appendChild(td);
    				
    				td =  document.createElement("td");
    				tn = document.createTextNode(attributeName);
    				td.appendChild(tn);
    				tr.appendChild(td);
    				
    				td =  document.createElement("td");
    				tn = document.createTextNode(dataType);
    				td.appendChild(tn);
    				tr.appendChild(td);
    				
    				i = document.createElement("i");
    				i.setAttribute("class", "fas fa-minus");
    				//i.setAttribute("onclick", "deleteRow(" + rowId + ")")
    				
    				button = document.createElement("button");
    				button.setAttribute("type", "button");
    				button.setAttribute("class", "btn btn-danger");
    				button.setAttribute("onclick", "deleteRow(" + rowId + ")");
    				button.appendChild(i);
    				
    				td =  document.createElement("td");
    				td.appendChild(button);
    				tr.appendChild(td);
    				
    				//div = document.createElement("div");
    				//div.setAttribute("class", "form-check");
    				//label = document.createElement("label");
    				//label.setAttribute("class", "form-check-label");
    				input = document.createElement("input");
    				input.setAttribute("type", "radio");
    				input.setAttribute("class", "form-check-input");
    				input.setAttribute("name", "optdecision");
    				input.setAttribute("onclick", "verifyChecked(this)");
    				//label.appendChild(input);
    				//div.appendChild(label);
    				
    				td =  document.createElement("td");
    				td.appendChild(input);
    				//td.appendChild(div);
    				tr.appendChild(td);
    				
    				i = document.createElement("i");
    				i.setAttribute("class", "fa icon-formula");
    				
    				button = document.createElement("button");
    				button.setAttribute("type", "button");
    				button.setAttribute("class", "btn btn-primary btn-advanced-formula btn-default");
    				button.setAttribute("data-placement", "bottom");
    				button.setAttribute("rel", "tooltip");
    				button.setAttribute("title", "");
    				button.setAttribute("data-original-title", "Alter the value of this field using a formula");
    				button.setAttribute("onclick", "defineFormula(" + rowId + ")");
    				button.appendChild(i);
    				
    				td =  document.createElement("td");
    				td.appendChild(button);
    				tr.appendChild(td);
    				
    				tbody.appendChild(tr);
    			}
    		}
    		
    		function checkDuplicity(parameters, tableId){
      		  var table, tr, tds, td, values;
      		  table = document.getElementById(tableId);
      		  tr = table.getElementsByTagName("tr");
      		  values = [];
      		
      		  for (i = 1; i < tr.length; i++) {
      			values = [];
    			tds = tr[i].getElementsByTagName("td");
    			for (j = 0; j < 3; j++){
    				values.push(tds[j].innerHTML);
    			}
    			console.log(parameters);
    			if(values.equals(parameters) == true){
    				return true;
    			}
      		  }
    			return false;
      		}
    		
    		// Warn if overriding existing method
    		if(Array.prototype.equals)
    		    console.warn("Overriding existing Array.prototype.equals. Possible causes: New API defines the method, there's a framework conflict or you've got double inclusions in your code.");
    		// attach the .equals method to Array's prototype to call it on any array
    		Array.prototype.equals = function (array) {
    		    // if the other array is a falsy value, return
    		    if (!array)
    		        return false;

    		    // compare lengths - can save a lot of time 
    		    if (this.length != array.length)
    		        return false;

    		    for (var i = 0, l=this.length; i < l; i++) {
    		        // Check if we have nested arrays
    		        if (this[i] instanceof Array && array[i] instanceof Array) {
    		            // recurse into the nested arrays
    		            if (!this[i].equals(array[i]))
    		                return false;       
    		        }           
    		        else if (this[i] != array[i]) { 
    		            // Warning - two different object instances will never be equal: {x:20} != {x:20}
    		            return false;   
    		        }           
    		    }       
    		    return true;
    		}
    		// Hide method from for-in loops
    		Object.defineProperty(Array.prototype, "equals", {enumerable: false});
    	</script>
    	
    	<script>
    		var attrId = "";
    		
    		function defineFormula(rowId){
    			attrId = rowId;
    			var div;
    			div = document.getElementById("advanced_formula_dialog");
    			div.setAttribute("class" ,"modal fade in");
    			div.setAttribute("style" ,"display: block;");
    			div.setAttribute("aria-hidden" ,"false");
    			console.log(rowId);
    		}
    		
    		function dismiss(){
    			attrId = "";
    			var div;
    			div = document.getElementById("advanced_formula_dialog");
    			div.setAttribute("class" ,"modal fade");
    			div.setAttribute("style" ,"display: none;");
    			div.setAttribute("aria-hidden" ,"true");
    		}
    		
    		function applyFormula(){
    			var formula, numExpr, exprForm, expressions, composition, list, temp, i, j;
    			console.log("Creando Concatenacion");
    			
    			var formula = "{\n  \"numExpr\":";
    			var numExpr = $("#expressions").val();
    			
    			console.log("Number of expresions: " + numExpr);
    			formula += numExpr + ",";
    			
    			formula += "\n  \"formula\": [\n";
    			
    			var exprForm = document.getElementById("exprForm");
    			var expressions = document.getElementsByClassName("form-row");
    			var composition;
    			var temp;
    			composition = expressions[0];
    			
    			for(i = 0; i < expressions.length; i++){
    				composition = expressions[i];
    				list = composition.childNodes;
    				formula += "    { \"id\":" + i + ", \"expr\":[ ";
    				for(j = 0; j < list.length; j++){
    	    			temp = list[j];
    	    			temp = temp.childNodes;
    	    			temp = temp[0];
    	    			temp = temp.childNodes;
    	    			temp = temp[1].value;
    	    			
    	    			if(j < list.length-1){
    	    				formula += "\"" + temp + "\","; 
    	    			}
    	    			else{
    	    				formula += "\"" + temp + "\" "; 
    	    			}
    				}
    				if(i < expressions.length-1){
	    				formula += "] },\n"; 
	    			}
	    			else{
	    				formula += "] }";
	    			}
    			}
    			
    			formula += "\n  ]\n}";
    			console.log(formula);
    			
    			var temp = attrId.childNodes;
    			temp = temp[temp.length-1];
    			var p = document.createElement("p");
    			p.setAttribute("hidden","");
    			p.innerHTML = formula;
    			temp.appendChild(p);
    		}
    		
    		$("#expressions").on("change", function(){
    		    console.log("event detected");
    			var value = $("#expressions").val();
    			if(value > 0){
    		    	console.log(value);
    		    	var form, label, select, option, div, div_inner, input;
    				form = document.getElementById("exprForm");
    				form.innerHTML = "";
    				
    				for(var i = 0; i < value; i++){
        				div = document.createElement("div");
            			div.setAttribute("class", "form-row");
            			
            			div_inner = document.createElement("div");
            			div_inner.setAttribute("class","col-auto");
            			label = document.createElement("label");
            			label.setAttribute("class", "sr-only");
            			label.setAttribute("class", "inlineFormInput");
            			label.innerHTML = "Comparation Value";
            			input = document.createElement("input");
            			input.setAttribute("type","text");
            			input.setAttribute("class","form-control mb-2");
            			input.setAttribute("placeholder","Insert Value");
            			label.appendChild(input);
            			div_inner.appendChild(label);
            			div.append(div_inner);
            			
            			div_inner = document.createElement("div");
            			div_inner.setAttribute("class","col-auto");
            			label = document.createElement("label");
            			label.setAttribute("for", "selectCondition");
            			label.innerHTML = "Select one of the following conditions:";
            			
            			select = document.createElement("select");
            			select.setAttribute("class", "form-control");
            			select.setAttribute("name", "conditions");
            			
            			option = document.createElement("option");
            			option.setAttribute("value", "equals");
            			option.innerHTML = "equals";
            			select.appendChild(option);
            			
            			option = document.createElement("option");
            			option.setAttribute("value", "like");
            			option.innerHTML = "like";
            			select.appendChild(option);
            			
            			option = document.createElement("option");
            			option.setAttribute("value", "not");
            			option.innerHTML = "not";
            			select.appendChild(option);
            			
            			option = document.createElement("option");
            			option.setAttribute("value", ">");
            			option.innerHTML = ">";
            			select.appendChild(option);
            			
            			option = document.createElement("option");
            			option.setAttribute("value", "<");
            			option.innerHTML = "<";
            			select.appendChild(option);
            			
            			option = document.createElement("option");
            			option.setAttribute("value", ">=");
            			option.innerHTML = ">=";
            			select.appendChild(option);
            			
            			option = document.createElement("option");
            			option.setAttribute("value", "<=");
            			option.innerHTML = "<=";
            			select.appendChild(option);
            			label.appendChild(select);
            			div_inner.appendChild(label);
            			div.append(div_inner);
            			
            			div_inner = document.createElement("div");
            			div_inner.setAttribute("class","col-auto");
            			label = document.createElement("label");
            			label.setAttribute("class", "sr-only");
            			label.setAttribute("class", "inlineFormInput");
            			label.innerHTML = "Output Value";
            			input = document.createElement("input");
            			input.setAttribute("type","text");
            			input.setAttribute("class","form-control mb-2");
            			input.setAttribute("placeholder","Insert Value");
            			label.appendChild(input);
            			div_inner.appendChild(label);
            			div.append(div_inner);
            			
            			form.appendChild(document.createElement("br"));
            			form.appendChild(div);
    				}
    			}	
    		});
    	</script>
    	<script>
    		function verifyChecked(button){
    			if(button.checked == true){
    				var button = document.getElementById("submitButton");
	    	    	button.removeAttribute("disabled");
	    	    	console.log("checked");
    				return true;    				
    			}
    			else{
    				console.log("not checked");
    				return false;	
    			}
    		}
    		  	
    		function submitTable(){
    			var table = document.getElementById("attrTable");
    			var rows = table.getElementsByTagName("tr");
    			var row, columns, set, data, button, p;
    			
    			set = [];
    			
    			for(var i = 1; i < rows.length; i++){
    				row = rows[i];
    				columns = row.getElementsByTagName("td");
    				data = [];
    				
    				data.push(columns[0].innerHTML);
    				data.push(columns[1].innerHTML);
    				data.push(columns[2].innerHTML);
    				data.push(columns[3].innerHTML);
    				
    				
    				button = columns[5].children;
    				button = button[0];
    				if(button.checked){
    					data.push("true");
    				}
    				else{
    					data.push("false");
    				}
    				
    				p = columns[6].getElementsByTagName("p");
    				if( p.length == 0){
    					data.push("");	
    				}
    				else{
    					data.push(p[0].innerHTML);
    				}
    				
    				set.push(data);
    				console.log(data);
    			}
    			console.log(set);
    			
    			var xhttp;
				xhttp = new XMLHttpRequest();
				xhttp.onreadystatechange = function(){
					if(this.readyState == 4 && this.status == 200){
						alert("Sended");
					}
				};
				xhttp.open("POST", "/GetProcessedDataServlet", true);
				xhttp.send(JSON.stringify(set));	
    		}
    	</script>
	</body>
</html>