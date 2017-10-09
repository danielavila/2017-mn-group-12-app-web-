    package Model;
    
    /**
     * UserTable class. Alternative for Database table
     */
    
    public class UserTable {
        private String id;
        private String nombre;
        private String segudoNombre;
        private String apellido;
        private int edad;
        private char sexo;
        private String telefono;
        private long codigoPostal;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getNombre() {
            return nombre;
        }

        public void setNombre(String nombre) {
            this.nombre = nombre;
        }

        public String getSegudoNombre() {
            return segudoNombre;
        }

        public void setSegudoNombre(String segudoNombre) {
            this.segudoNombre = segudoNombre;
        }

        public String getApellido() {
            return apellido;
        }

        public void setApellido(String apellido) {
            this.apellido = apellido;
        }

        public int getEdad() {
            return edad;
        }

        public void setEdad(int edad) {
            this.edad = edad;
        }

        public char getSexo() {
            return sexo;
        }

        public void setSexo(char sexo) {
            this.sexo = sexo;
        }

        public String getTelefono() {
            return telefono;
        }

        public void setTelefono(String telefono) {
            this.telefono = telefono;
        }

        public long getCodigoPostal() {
            return codigoPostal;
        }

        public void setCodigoPostal(long codigoPostal) {
            this.codigoPostal = codigoPostal;
        }
        
    }