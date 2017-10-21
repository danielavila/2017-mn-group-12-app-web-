<h2>Aplicar Metodologia</h2>
   <p id="status"></p>
  <form action="" method="POST" role="form">
  
  
      <div class="form-group">
    <label >Seleccione la Metodologia </label>

	<select id="elegirMetodologia" name="nombre">
		<ul>
    <#list metodologias as metodologia>
        <li><option value=${metodologia.nombre}> ${metodologia.nombre} </option></li>        
    </#list>
		</ul> 
			</select>
		
			</div>
  

   
<div class="form-group">
<label >Seleccione las empresas </label>

	<div class="form-group">
		
       <input type="checkbox" id="myCheck" name="empresa1" value="Facebook">Facebook<br> 
       <input type="checkbox" id="myCheck" name="empresa2" value="Pepsico">Pepsico<br> 
       <input type="checkbox" id="myCheck" name="empresa3" value="CocaCola">CocaCola<br> 
       <input type="checkbox" id="myCheck" name="empresa4" value="Twitter">Twitter<br> 

		
		
   <input type="submit" value="Aplicar Metodologia">
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


