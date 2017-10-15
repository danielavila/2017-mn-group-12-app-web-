<h2>Crear Metodologia</h2>
   <p id="status"></p>
  <form action="" method="POST" role="form">
  
    <div class="form-group">
      <label for="nombre">Nombre metodologia</label>
      <input type="text" class="form-control" id="nombre" name="nombre" placeholder="Nombre">
    </div>
    <div class="form-group">
      <button type="submit" class="btn btn-default">Crear metodologia</button>
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

<button id="executelink">Agregar condicion</button>
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
            url: "crearMetodologia",
            data: json,
            dataType: "json",
            success : function() {
                $("#status").text("Metodologia creada, ahora agregue condiciones");
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

