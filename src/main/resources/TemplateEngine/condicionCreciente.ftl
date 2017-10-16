<h2>Condicion Creciente</h2>
   <p id="status"></p>
  <form action="" method="POST" role="form">
    <div class="form-group">
      <label for="anios">Ingrese los anios </label>
      <input type="text" class="form-control" id="anios" name="anios" placeholder="anios">
    </div>
  

  <div class="form-group">
	<select nombreIndicador="nombreIndicador">
		<ul>
    <#list indicadores as nombreIndicador>
        <li><option value=${nombreIndicador}> ${nombreIndicador} </option></li>        
    </#list>
		</ul> 
			</select>
		<button nombreIndicador="nombreIndicador">Seleccionar Indicador</button>
		
			</div>

		<div class="form-group">
		
    <button type="submit" class="btn btn-default">Crear condicion</button>
    </div>
    <div class="form-group">
       <#assign var_link = "http://localhost:4567/crearMetodologia">

<a href="${var_link}">Volver al menu metodologia</a>
       </div>
  </form>

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


