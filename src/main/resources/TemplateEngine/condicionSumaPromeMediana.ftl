<h2>Condicion </h2>
   <p id="status"></p>
  <form action="" method="POST" role="form">
    <div class="form-group">
      <label for="nombreIndicador">Ingrese el nombre del indicador </label>
      <input type="text" class="form-control" id="nombreIndicador" name="nombreIndicador" placeholder="debe comenzar con i_">
    </div>   
     <div class="form-group">
      <label for="comparador">Ingrese el operador para armar la ecuacion </label>
      <input type="text" class="form-control" id="comparador" name="comparador" placeholder="<,>,<=,>=">
    </div>
    <div class="form-group">
      <label for="valorAcomparar">Ingrese el valor contra el que desea comparar </label>
      <input type="text" class="form-control" id="valorAcomparar" name="valorAcomparar" placeholder=" ">
    </div>
   
      <div class="form-group">
      <label for="ordenamiento">Ingrese un criterio de ordenamiento </label>
      <input type="text" class="form-control" id="ordenamiento" name="ordenamiento" placeholder="mayorAmenor o menorAmayor">
    </div>
    <div class="form-group">
      <label for="fechaInicio">Fecha inicio del periodo</label>
      <input type="text" class="form-control" id="fechaInicio" name="fechaInicio" placeholder="Ingrese fecha inicio del periodo dd/mm/aaaa">
     </div>
    <div class="form-group">
      <label for="fechaFin">Fecha fin del periodo</label>
      <input type="text" class="form-control" id="fechaFin" name="fechaFin" placeholder="Ingrese fecha fin del periodo dd/mm/aaaa">
       </div>
    
    
    <button type="submit" class="btn btn-default">Crear condicion</button>
    
    <div class="form-group">
       <#assign var_link = "http://localhost:4567/crearMetodologia">

<a href="${var_link}">Volver al menu metodologia</a>
       </div>
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
            url: "condicionSumatoria",
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