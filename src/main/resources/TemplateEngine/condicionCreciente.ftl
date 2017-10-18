<h2>Condicion Creciente</h2>
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
<button id="seleccionarIndicador">Crear Condicion</button>
		 </div>
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
       <p id="status"></p>
           <form action="" method="POST" role="form">
    <div class="form-group">
      <label for="nombreIndicador"> </label>
      <input type="text" class="form-control" id=id name="nombreIndicador" placeholder="">
    </div>
    </form>
    
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
            url: "condicionCreciente",
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


