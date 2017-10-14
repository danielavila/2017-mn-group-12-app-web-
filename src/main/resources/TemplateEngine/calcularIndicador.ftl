<h2>Calcular Indicador</h2>
   <p id="status"></p>
  <form action="" method="POST" role="form">
    <div class="form-group">
      <label for="nombreEmpresa">Empresa </label>
      <input type="text" class="form-control" id="nombreEmpresa" name="nombreEmpresa" placeholder="Nombre de Empresa">
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
   <label for="indicadores">Seleccione el indicador</label>
    <input type="text" class="form-control" id="nombreIndicador" name="nombreIndicador" placeholder="Ingrese el nombre del indicador (comienza con i_)">
    	</div>
    <button type="submit" class="btn btn-default">Submit</button>
  </form>


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