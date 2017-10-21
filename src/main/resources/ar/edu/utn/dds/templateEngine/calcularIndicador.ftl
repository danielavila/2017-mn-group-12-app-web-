<h2>Calcular Indicador</h2>
   <p id="status"></p>
  <form action="" method="POST" role="form">
    <div class="form-group">
    <label >Seleccione la empresa </label>

	<select id="elegirEmpresa" name="nombreEmpresa">
		<ul>
    <#list empresas as empresa>
        <li><option value=${empresa.nombre}> ${empresa.nombre} </option></li>        
    </#list>
		</ul> 
			</select>
		
			</div>

    <div class="form-group">
      <label for="fechaInicio">Fecha inicio del periodo</label>
      <input type="text" class="form-control" id="fechaInicio" name="fechaInicio" placeholder="Ingrese fecha inicio del periodo dd/mm/aaaa">
     </div>
    <div class="form-group">
      <label for="fechaFin">Fecha fin del periodo</label>
      <input type="text" class="form-control" id="fechaFin" name="fechaFin" placeholder="Ingrese fecha fin del periodo dd/mm/aaaa">
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
        <input type="submit" id="register" value="Calcular indicador" disabled="disabled" />
          </div>
  </form>
  
<script type="text/javascript">
    $(document).ready(function() {
        var  id = "";
        $("#elegirEmpresa").change(function() {
            $id=$("#elegirEmpresa option:selected").val();
        });
        $("#calcular").click(function() {
       <p id="status"></p>
           <form action="" method="POST" role="form">
    <div class="form-group">
      <label for="nombreEmpresa"> </label>
      <input type="text" class="form-control" id=id name="nombreEmpresa" placeholder="">
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
            url: "calcularIndicador",
            data: json,
            dataType: "json",
            success : function() {
                $("#status").text("Aca va el resultado");
                this_.find('input, select').val('');
            },
            error : function(e) {
                $("#status").text(e.responseText);
            }
        });
        $("html, body").animate({ scrollTop: 0 }, "slow");
        return false;
    });
});

</script>
<script>
$('#elegirEmpresa,#fechaInicio,#fechaFin,#elegirIndicador').bind('keyup', function() {
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