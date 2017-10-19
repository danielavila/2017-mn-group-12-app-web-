<h2>Aplicar Metodologia</h2>
   <p id="status"></p>
  <form action="" method="POST" role="form">
  
    <div class="form-group">
      <label for="nombre">Nombre metodologia</label>
      <input type="text" class="form-control" id="nombre" name="nombre" placeholder="Nombre">
    </div>
   
<div class="form-group">
<label >Seleccione las empresas </label>

	
		<ul>
    <#list empresas as empresa>
        <li><input type="checkbox" id="myCheck" name="empresas" value=${empresa.nombre}><br> ${empresa.nombre}
        
        </li>        
    </#list>
		</ul> 
	
		
			</div>

<div class="form-group">
<button id="aplicarMetodologia">Aplicar Metodologia</button>
		 </div>
 
  </form>


<script>
function getCheckedBoxes(chkboxName) {
  var checkboxes = document.getElementsByName(chkboxName);
  var checkboxesChecked = [];
  // loop over them all
  for (var i=0; i<checkboxes.length; i++) {
     // And stick the checked ones onto an array...
     if (checkboxes[i].checked) {
        checkboxesChecked.push(checkboxes[i]);
     }
  }
  // Return the array if it is non-empty, or null
  return checkboxesChecked.length > 0 ? checkboxesChecked : null;
}
</script>

<script type="text/javascript">
    $(document).ready(function() {
        var action = new Array();
        $("#aplicarMetodologia").click(function() {
      action =  $('input[name="empresas"]:checked').serialize();
        alert(action);
      console.log(action);

      
    
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


