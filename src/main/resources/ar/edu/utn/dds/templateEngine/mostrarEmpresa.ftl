<style>
 table th a {
 	text-transform: capitalize;
 }
 </style>

   <div class="starter-template">
    	<h2> Empresas </h2>
    	<div class="tablaEmpresa">
    	 </div>
		<div class="paginationContainer "></div>	
    </div>	
 	<script src="js/awesomeTable.js" type="text/javascript"></script>
 	<script>
 		$( document ).ready(function() {
 			$.getJSON('/getempresas',function(json){
    			if ( json.length == 0 ) {
        			console.log("NO DATA!");
        			$(".tablaEmpresa").text("No se encuentran empresas");
    			}
    			else {
    				var tbl = new awesomeTableJs({
			
						data:json,					
						tableWrapper:".tablaEmpresa",
						paginationWrapper:".paginationContainer",
						buildPageSize: false,
						buildSearch: false,
						
					});
					tbl.createTable();	
    			}
			});
 			
		});
	
		</script>
	
	

<select id="picksite">
<ul>
    <#list empresas as empresa>
        <li><option value=${empresa.id}> ${empresa.nombre} </option></li>        
    </#list>

</select>

<button id="executelink">Ir a las cuentas</button>
	
	

		<script type="text/javascript">
    $(document).ready(function() {
        var newUrl = "";
        $("#picksite").change(function() {
        var id=$("#picksite option:selected").val();
            $newUrl = "http://localhost:4567/getCuentas/"+id ;
        });
        $("#executelink").click(function() {
            location = $newUrl ;
        });
    });
</script>


	
	
	
	
	
	
	
	
	
	
	
	
	
	
	