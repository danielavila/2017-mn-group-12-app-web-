<div class="starter-template">

<h2>${title}</h2>

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
					<form class="navbar-form navbar-right">
						<div class="form-group">
							<input type="text" placeholder="usuario" id= "usuario" class="form-control" />
						</div>
						<div class="form-group">
							<input type="password" placeholder="contrasenia" id = "contrasenia"
								class="form-control" />
						</div>
						<button type="button" id="executelink" class="btn btn-success">Sign in</button>
						
					</form>
				</div>
				<!--/.navbar-collapse -->
			</div>
		</nav>

		<br /> <br /> <br /> <br />
	</div>
	</div> <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
	<script type="text/javascript">
		
		    $(document).ready(function() {
				
			$("#executelink").click(function() {
				var usuario= $('#usuario').val();
				var contrasenia= $('#contrasenia').val(); 
							  
				$.ajax({
					url: '/app/log',
					type: "POST",
					data:"submit=&usuario="+usuario+"&contrasenia="+contrasenia,
					success: function(datos){
						window.location.replace("http://localhost:4567/home");
					}});
				return false;
				});
			});
		    
	</script>
