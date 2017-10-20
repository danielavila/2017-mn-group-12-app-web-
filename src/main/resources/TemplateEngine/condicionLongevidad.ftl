<h2>Condicion Longevidad</h2>
   <p id="status"></p>
  <form action="" method="POST" role="form">
    <div class="form-group">
      <label for="anios">Ingrese los anios </label>
      <input type="text" class="form-control" id="anios" name="anios" placeholder="anios">
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
            url: "condicionLongevidad",
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