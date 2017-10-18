<h2>Aplicar Metodologia</h2>
   <p id="status"></p>
  <form action="" method="POST" role="form">
  
    <div class="form-group">
      <label for="nombre">Nombre metodologia</label>
      <input type="text" class="form-control" id="nombre" name="nombre" placeholder="Nombre">
    </div>
    <div class="form-group">
      <label for="empresa">Nombre empresa</label>
      <input type="text" class="form-control" id="empresa" name="empresa" placeholder="Nombre">
    </div>
    
    
    <div class="container">
    <input type="checkbox" /> Facebook <br />
    <input type="checkbox" /> Pepsi <br />
    <input type="checkbox" /> CocaCola <br />
    <input type="checkbox" /> Twitter <br />
 
</div>

   <div class="form-group">
      <button type="submit" class="btn btn-default">Agregar empresa</button>
      </div>
    <div class="form-group">
      <button type="submit" class="btn btn-default">Aplicar metodologia</button>
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
            url: "aplicarMetodologia",
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


