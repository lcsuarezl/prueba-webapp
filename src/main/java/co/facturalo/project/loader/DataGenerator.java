package co.facturalo.project.loader;

import java.text.Normalizer;
import java.util.Date;
import java.util.Random;
import java.util.regex.Pattern;

/**
 * Facilita la generacion de datos para diferentes estructuras
 * 
 * @author leon
 *         <ol>
 *         <li>Nombres Hombre</li>
 *         <li>Nombres Mujer</li>
 *         <li>Apellidos</li>
 *         <li>Numeros aleatorios de k digitos</li>
 *         <li>Parrafos de k palabras</li>
 *         <li></li>
 *         <li></li>
 *         <li></li>
 *         <li></li>
 *         <li></li>
 *         </ol>
 *
 */
public class DataGenerator {

	private String[] nomMuj = { "Isabella", "Olivia", "Alexis", "Sofía", "Victoria", "Amelia", "Alexa", "Julia",
			"Camila", "Alexandra", "Maya", "Andrea", "Ariana", "María", "Eva", "Angelina", "Valeria", "Natalia",
			"Isabel", "Sara", "Liliana", "Adriana", "Juliana", "Gabriela", "Daniela", "Valentina", "Lila", "Vivian",
			"Nora", "Ángela", "Elena", "Clara", "Eliana", "Alana", "Miranda", "Amanda", "Diana", "Ana", "Penélope",
			"Aurora", "Alexandría", "Lola", "Alicia", "Amaya", "Alexia", "Jazmín", "Mariana", "Alina", "Lucía",
			"Fátima", "Ximena", "Laura", "Cecilia", "Alejandra", "Esmeralda", "Verónica", "Daniella", "Miriam",
			"Carmen", "Iris", "Guadalupe", "Selena", "Fernanda", "Angélica", "Emilia", "Lía", "Tatiana", "Mónica",
			"Carolina", "Jimena", "Dulce", "Talía", "Estrella", "Brenda", "Lilian", "Paola", "Serena", "Celeste",
			"Viviana", "Elisa", "Melina", "Gloria", "Claudia", "Sandra", "Marisol", "Asia", "Ada", "Rosa", "Isabela",
			"Regina", "Elsa", "Perla", "Raquel", "Virginia", "Patricia", "Linda", "Marina", "Leila", "América",
			"Mercedes" };

	private String[] nomHomb = { "Daniel", "David", "Gabriel", "Benjamín", "Samuel", "Lucas", "Ángel", "José", "Adrián",
			"Sebastián", "Xavier", "Juan", "Luis", "Diego", "Óliver", "Carlos", "Jesús", "Alex", "Maximiliano",
			"Alejandro", "Antonio", "Miguel", "Víctor", "Joel", "Santiago", "Elías", "Iván", "Óscar", "Leonardo",
			"Eduardo", "Alan", "Nicolás", "Jorge", "Omar", "Paúl", "Andrés", "Julián", "Josué", "Román", "Fernando",
			"Javier", "Abraham", "Ricardo", "Francisco", "César", "Mario", "Manuel", "Édgar", "Alexis", "Israel",
			"Mateo", "Héctor", "Sergio", "Emiliano", "Simón", "Rafael", "Martín", "Marco", "Roberto", "Pedro",
			"Emanuel", "Abel", "Rubén", "Fabián", "Emilio", "Joaquín", "Marcos", "Lorenzo", "Armando", "Adán", "Raúl",
			"Julio", "Enrique", "Gerardo", "Pablo", "Jaime", "Saúl", "Esteban", "Gustavo", "Rodrigo", "Arturo",
			"Mauricio", "Orlando", "Hugo", "Salvador", "Alfredo", "Maximiliano", "Ramón", "Ernesto", "Tobías", "Abram",
			"Noél", "Guillermo", "Ezequiel", "Lucián", "Alonzo", "Felipe", "Matías", "Tomás", "Jairo", };

	private String[] apellidos = { "Acosta", "Acuña", "Agüero", "Aguirre", "Álvarez", "Arce", "Arias", "Ávila",
			"Barrios", "Benítez", "Blanco", "Bravo", "Cabrera", "Cáceres", "Campos", "Cardozo", "Carrizo", "Castillo",
			"Castro", "Chávez", "Córdoba", "Coronel", "Correa", "Cruz", "Díaz", "Domínguez", "Duarte", "Escobar",
			"Farías", "Fernández", "Ferreyra", "Figueroa", "Flores", "Franco", "García", "Giménez", "Godoy", "Gómez",
			"González", "Gutiérrez", "Hernández", "Herrera", "Juárez", "Ledesma", "Leguizamón", "Leiva", "López",
			"Lucero", "Luna", "Maidana", "Maldonado", "Mansilla", "Martin", "Martínez", "Medina", "Méndez", "Mendoza",
			"Miranda", "Molina", "Montenegro", "Morales", "Moreno", "Moyano", "Muñoz", "Navarro", "Núñez", "Ojeda",
			"Olivera", "Ortíz", "Páez", "Paz", "Peralta", "Pereyra", "Pérez", "Ponce", "Quiroga", "Ramírez", "Ramos",
			"Ríos", "Rivero", "Rodríguez", "Rojas", "Roldán", "Romero", "Ruiz", "Sánchez", "Silva", "Soria", "Sosa",
			"Soto", "Suárez", "Toledo", "Torres", "Valdéz", "Vargas", "Vázquez", "Vega", "Velázquez", "Vera",
			"Villalba" };

	private String[] dominios = { "google.com", "hotmail.com", "yahoo.com", "outlook.com", "mail.com" };

	private String[] module = { "users", "contacts", "items" };

	private String[] actions = { "create", "view", "list", "update", "delete" };

	private String loremIpsum = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed consectetur pellentesque ligula sed pulvinar. Integer auctor molestie velit varius ultricies. Suspendisse ac elementum libero, sed ornare ipsum. Donec volutpat venenatis elit. Phasellus at turpis in urna elementum rhoncus. Praesent dictum placerat luctus. In fermentum nulla eget nunc consectetur, elementum tempus metus pretium. Maecenas venenatis lectus sit amet enim vulputate malesuada. Nullam non felis auctor, sagittis felis at, facilisis tortor Maecenas at lectus eget nunc molestie iaculis. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Phasellus malesuada fringilla orci, et pellentesque massa condimentum nec. Quisque tempor dolor sed interdum rhoncus. Suspendisse mollis fringilla magna a ultrices. Etiam in diam sit amet massa pulvinar consequat. Maecenas tortor lorem, volutpat id enim quis, feugiat feugiat magna. Nullam non tincidunt velit. Quisque sollicitudin ac nisi eu rhoncus. Morbi tempus bibendum metus, a dictum elit commodo nec. Curabitur non dui facilisis, elementum quam eu, placerat justo. Cras feugiat ut diam non luctus. Praesent porta vitae orci at mattis. Vivamus ullamcorper quam vel venenatis congue. Aenean vehicula egestas orci, a consequat quam iaculis non. Proin ultrices tincidunt lobortis. Integer blandit nunc tellus. In egestas ullamcorper ultricies. Etiam imperdiet vestibulum vestibulum. Praesent adipiscing metus nisl, dapibus egestas nisl porttitor a. Nullam tristique fringilla lorem ac aliquet. Aliquam metus ipsum, tristique nec lobortis quis, lobortis eu ante. Suspendisse potenti. Etiam leo felis, consequat quis felis a, interdum consectetur dolor. Proin tristique diam ac quam scelerisque, at fermentum lorem porta. Suspendisse porttitor ultrices magna vitae mollis. Aliquam elit felis, vulputate eu leo sed, scelerisque congue risus. Cras porta a lorem sed fringilla. Sed feugiat dui eu orci pharetra vehicula. In id rhoncus arcu. Nam nec pulvinar massa, sit amet euismod eros. Quisque lobortis urna eu mi fringilla, vitae fermentum sapien feugiat. Morbi egestas velit eget odio sagittis, eget molestie leo pharetra. Morbi hendrerit erat sit amet dictum tincidunt. Vivamus interdum, lacus quis congue viverra, leo nunc posuere massa, eget adipiscing eros mauris at augue. Cras tortor nisl, auctor at orci quis, posuere sagittis justo. Cras et pulvinar leo. Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos. Quisque ac sodales diam. Mauris sit amet pharetra ante. Sed urna neque, pulvinar nec risus nec, accumsan elementum odio. Morbi eu augue nec leo sodales porta. Curabitur cursus nulla dui, eu feugiat ligula mollis id. Proin id tortor non dui sagittis posuere. Fusce justo mauris, tempor id ante sed, pellentesque mollis urna. Vivamus pellentesque metus sed facilisis iaculis. Aliquam felis magna, dictum non velit non, molestie mattis diam. Cras commodo vehicula odio, vitae elementum turpis tincidunt in. Etiam eu felis at tortor fermentum elementum laoreet sed velit. Nam sit amet purus id leo consequat placerat nec in dolor.";

	private int nomMujLgt = nomMuj.length;
	private int nomHomLgt = nomHomb.length;
	private int apeLgt = apellidos.length;
	private int domLgt = dominios.length;
	private int moduleLgt = module.length;
	private int actionsLgt = actions.length;
	private int textLgt = loremIpsum.length();

	private Random rand = new Random();

	/**
	 * @return Retorna el nombre de un hombre
	 */
	public String getNomHom() {
		return nomHomb[rand.nextInt(nomHomLgt)];
	}

	/**
	 * @return Retorna el nombre de una mujer
	 */
	public String getNomMuj() {
		return nomMuj[rand.nextInt(nomMujLgt)];
	}

	/**
	 * @return Retorna un apellido
	 */
	public String getApe() {
		return apellidos[rand.nextInt(apeLgt)];
	}

	/**
	 * @return Retorna un dominio
	 */
	public String getDomain() {
		return dominios[rand.nextInt(domLgt)];
	}

	/**
	 * @return Retorna un modulo en el sistema
	 */
	public String getModule() {
		return module[rand.nextInt(moduleLgt)];
	}

	/**
	 * @return Retorna una accion en el sistema
	 */
	public String getAction() {
		return actions[rand.nextInt(actionsLgt)];
	}

	public String getText(int size) {
		if (size > textLgt)
			size = textLgt;
		return loremIpsum.substring(0, size);
	}

	/**
	 * Retorna una cadena de texto de solo numeros de tamano size
	 * 
	 * @param size
	 *            longitud de la cadena de texto a generar
	 * @return cadena de digitos de longitud size
	 */
	public String getNumberByLengt(int size) {
		String num = "";
		for (int i = 0; i < size; i++) {
			num.concat("" + rand.nextInt(9));
		}
		return num;
	}

	/**
	 * Retorna una cadena de texto de un numero menor o igual bound
	 * 
	 * @param bound
	 *            el valor maximo del numero a generar
	 * @return cadena de digitos menor o igual a bound
	 */
	public String getNumber(int bound) {
		return "" + rand.nextInt(bound);
	}

	/**
	 * @param name
	 *            Nombre del usuario al que se le genera el correo
	 * @param lastName
	 *            Apellido del usuario al que se le genera el correo
	 * @return una direccion de correo
	 */
	public String getMail(String name, String lastName) {
		String mail = name.substring(0, 4) + lastName.substring(0, 4) + "@" + dominios[rand.nextInt(domLgt)];
		return normalizer(mail);
	}

	/**
	 * @return Genera una ruta de una aplicacion algo como user/update
	 */
	public String getPath() {
		return "http://localhost:8080/prueba-webapp/faces/" + getModule() + "/" + getAction();
	}

	/**
	 * Elimina caracteres extrannos a un String como tildes, ascentos, ennes,
	 * etc
	 * 
	 * @param string
	 *            cadena de texto a normalizar
	 * @return cadena de texto normalizada
	 */
	public String normalizer(String string) {
		String normalized = Normalizer.normalize(string, Normalizer.Form.NFD);
		// Nos quedamos únicamente con los caracteres ASCII
		Pattern pattern = Pattern.compile("\\P{ASCII}+");
		return pattern.matcher(normalized).replaceAll("");
	}

	public Date getDate() {
		return new Date(System.currentTimeMillis() - nextLong(rand, 31536000000l));
	}

	long nextLong(Random rng, long n) {
		// error checking and 2^x checking removed for simplicity.
		long bits, val;
		do {
			bits = (rng.nextLong() << 1) >>> 1;
			val = bits % n;
		} while (bits - val + (n - 1) < 0L);
		return val;
	}

}
