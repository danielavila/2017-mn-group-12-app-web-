<h2>Condicion Decreciente</h2>
   <p id="status"></p>
  <form action="" method="POST" role="form">
  
    <div class="form-group">
      <label for="anios">Ingrese los anios </label>
      <input type="text" class="form-control" id="anios" name="anios" placeholder="anios">
    </div>
     <div class="form-group">
     <label >Seleccione el indicador </label>

	<select id="elegirIndicador" name="nombreIndicador">
		<ul>
    <#list indicadores as indicador>
        <li><option value=${indicador.nombre}> ${indicador.nombre} </option></li>        
    </#list>
		</ul> 
			</select>
		
			</div>
 
    <div class="form-group">
        <input type="submit" id="register" value="Crear Condicion" disabled="disabled" />
         
    
    </div>
    
    <div class="form-group">
   <select id="picksite">
    <option value="">Agregue Condiciones</option>
    <option value="http://localhost:4567/condicionLongevidad">Longevidad</option>
       <option value="http://localhost:4567/condicionCreciente">Creciente</option>
          <option value="http://localhost:4567/condicionDecreciente">Decreciente</option>
             <option value="http://localhost:4567/condicionSumatoria">Sumatoria</option>
                <option value="http://localhost:4567/condicionPromedio">Promedio</option>
                   <option value="http://localhost:4567/condicionMediana">Mediana</option>
</select>

<button id="executelink">Agregar otra condicion a la metodologia</button>
       </div>
       
       
       <font size="3" color="red">Regrese al menu crear metodologia para finalizar de cargarle las condiciones a la actual metodologia</font>
    <div class="form-group">
       <#assign var_link = "http://localhost:4567/crearMetodologia">

<a href="${var_link}">Volver al menu metodologia</a>
       </div>
  </form>

<script type="text/javascript">
    $(document).ready(function() {
        var  id = "";
        $("#elegirIndicador").change(function() {
            $id=$("#elegirIndicador option:selected").val();
        });
        $("#seleccionarIndicador").click(function() {
      
      
    
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
<!-- Simple JS Function to convert the data into JSON and Pass it as ajax Call --!>
<script>
$(function() {
    $('form').submit(function(e) {
        e.preventDefault();
        var this_ = $(this);
        var array = this_.serializeArray();
        var json = {};
    
        $.each(array, function() {
            json[this.name] = this.value || '';
        });
        json = JSON.stringify(json);
    
        // Ajax Call
        $.ajax({
            type: "POST",
            url: "condicionDecreciente",
            data: json,
            dataType: "json",
            success : function() {
                $("#status").text("Condicion creada exitosamente");
                this_.find('input,select').val('');
            },
            error : function(e) {
                console.log(e.responseText);
                $("#status").text(e.responseText);
            }
        });
        $("html, body").animate({ scrollTop: 0 }, "slow");
        return false;
    });
});

</script>


<script>
$('#anios,#elegirIndicador').bind('keyup', function() {
    if(allFilled()) $('#register').removeAttr('disabled');
});
</script>
<script>
function allFilled() {
    var filled = true;
    $('body input').each(function() {
        if($(this).val() == '') filled = false;
    });
    return filled;
}
</script>
