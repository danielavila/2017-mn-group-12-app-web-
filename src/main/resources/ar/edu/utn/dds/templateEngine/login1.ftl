<div class="starter-template">



</div>
<div class="container">
		<nav class="navbar navbar-inverse navbar-fixed-top">
			<div class="container">
				<div class="navbar-header">
					<button type="button" class="navbar-toggle collapsed"
						data-toggle="collapse" data-target="#navbar" aria-expanded="false"
						aria-controls="navbar">
						<span class="sr-only">Toggle navigation</span> <span
							class="icon-bar"></span> <span class="icon-bar"></span> <span
							class="icon-bar"></span>
					</button>
					<a class="navbar-brand" href="#">TP DDS 12</a>
				</div>
				<div id="navbar" class="navbar-collapse collapse">
					<form action="" method="POST" role="form" class="navbar-form navbar-right">
						<div class="form-group">
							<input type="text" placeholder="usuario" id= "usuario" name ="nombre" class="form-control" />
						</div>
						<div class="form-group">
							<input type="password" placeholder="contrasenia" id = "contrasenia" name="contrasenia"
								class="form-control" />
						</div>
						<input type="submit"  onclick="check(this.form)" id="executelink" class="btn btn-success">Sign in</button>
						
					</form>
				</div>
				<!--/.navbar-collapse -->
			</div>
		</nav>

		<br /> <br /> <br /> <br />
	</div>
	</div> 
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
            url: "login",
            data: json,
            dataType: "json",
            success : function() {
                
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
<script language="javascript">
function check(form)/*function to check userid & password*/
{
 /*the following code checkes whether the entered userid and password are matching*/
 if(form.usuario.value == "Lucas" && form.contrasenia.value == "123")
  {
    window.location.replace("http://localhost:4567/home");
  }
  else{ 
  if(form.usuario.value == "Nicolas" && form.contrasenia.value == "123")
  {
    window.location.replace("http://localhost:4567/home");
  }
  else{
   if(form.usuario.value == "Gabriel" && form.contrasenia.value == "123")
  {
    window.location.replace("http://localhost:4567/home");
  }
  else{
   if(form.usuario.value == "Daniel" && form.contrasenia.value == "123")
  {
    window.location.replace("http://localhost:4567/home");
  }
  
 else
 {
   alert("Error Password or Username")/*displays error message*/
  }}}}
}
</script>
