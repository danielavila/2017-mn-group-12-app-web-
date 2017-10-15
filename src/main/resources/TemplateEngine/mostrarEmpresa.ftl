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
	
	<script type="text/javascript">
    $(document).ready(function() {
        var newUrl = "";
        $("#picksite").change(function() {
            $newUrl = $("#picksite option:selected").val();
        });
        $("#executelink").click(function() {
            location = $newUrl ;
        });
    });
</script>

<select id="picksite">
    <option value="">Seleccione la empresa </option>
    <option value="http://localhost:4567/getCuentas/38">Pepsico</option>
    <option value="http://localhost:4567/getCuentas/28">Twitter</option>
    <option value="http://localhost:4567/getCuentas/19">CocaCola</option>
    <option value="http://localhost:4567/getCuentas/10">Facebook</option>
</select>

<button id="executelink">Ir a las cuentas</button>
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	