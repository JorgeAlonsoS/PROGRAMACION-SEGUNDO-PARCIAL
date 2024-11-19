import java.util.*;

class Libro {
    private String titulo;
    private String autor;
    private String isbn;
    private boolean disponible;

    public Libro(String titulo, String autor, String isbn) {
        this.titulo = titulo;
        this.autor = autor;
        this.isbn = isbn;
        this.disponible = true;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getIsbn() {
        return isbn;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void prestar() {
        disponible = false;
    }

    public void devolver() {
        disponible = true;
    }

    @Override
    public String toString() {
        return "Libro [Título: " + titulo + ", Autor: " + autor + ", ISBN: " + isbn + ", Disponible: " + disponible
                + "]";
    }
}

class Usuario {
    private String nombre;
    private String id;
    private List<Libro> librosPrestados;
    private static final int LIMITE_LIBROS = 3;

    public Usuario(String nombre, String id) {
        this.nombre = nombre;
        this.id = id;
        this.librosPrestados = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public boolean prestarLibro(Libro libro) {
        if (librosPrestados.size() >= LIMITE_LIBROS) {
            System.out.println(nombre + " ya tiene el límite de libros prestados.");
            return false;
        }

        if (libro.isDisponible()) {
            libro.prestar();
            librosPrestados.add(libro);
            System.out.println(nombre + " ha tomado prestado el libro: " + libro.getTitulo());
            return true;
        } else {
            System.out.println("El libro \"" + libro.getTitulo() + "\" no está disponible.");
            return false;
        }
    }

    public boolean devolverLibro(Libro libro) {
        if (librosPrestados.contains(libro)) {
            libro.devolver();
            librosPrestados.remove(libro);
            System.out.println(nombre + " ha devuelto el libro: " + libro.getTitulo());
            return true;
        } else {
            System.out.println(nombre + " no tiene prestado el libro: " + libro.getTitulo());
            return false;
        }
    }

    @Override
    public String toString() {
        return "Usuario [Nombre: " + nombre + ", ID: " + id + ", Libros Prestados: " + librosPrestados.size() + "]";
    }
}

class GestionBiblioteca {
    private List<Libro> catalogo;
    private List<Usuario> usuarios;

    public GestionBiblioteca() {
        this.catalogo = new ArrayList<>();
        this.usuarios = new ArrayList<>();
    }

    public void registrarLibro(Libro libro) {
        catalogo.add(libro);
        System.out.println("Libro registrado: " + libro.getTitulo());
    }

    public void registrarUsuario(Usuario usuario) {
        usuarios.add(usuario);
        System.out.println("Usuario registrado: " + usuario.getNombre());
    }

    public Usuario buscarUsuario(String id) {
        for (Usuario usuario : usuarios) {
            if (usuario.getId().equals(id)) {
                return usuario;
            }
        }
        return null;
    }

    public Libro buscarLibro(String isbn) {
        for (Libro libro : catalogo) {
            if (libro.getIsbn().equals(isbn)) {
                return libro;
            }
        }
        return null;
    }

    public void mostrarCatalogo() {
        System.out.println("Catálogo de Libros:");
        for (Libro libro : catalogo) {
            System.out.println(libro);
        }
    }

    public void mostrarUsuarios() {
        System.out.println("Usuarios registrados:");
        for (Usuario usuario : usuarios) {
            System.out.println(usuario);
        }
    }
}

public class SistemaBiblioteca {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        GestionBiblioteca biblioteca = new GestionBiblioteca();

        while (true) {
            System.out.println("\n--- Menú ---");
            System.out.println("1. Registrar libro");
            System.out.println("2. Registrar usuario");
            System.out.println("3. Mostrar catálogo");
            System.out.println("4. Mostrar usuarios");
            System.out.println("5. Prestar libro");
            System.out.println("6. Devolver libro");
            System.out.println("7. Salir");
            System.out.print("Seleccione una opción: ");
            int opcion = scanner.nextInt();
            scanner.nextLine(); // Consumir nueva línea

            switch (opcion) {
                case 1:
                    System.out.print("Ingrese título del libro: ");
                    String titulo = scanner.nextLine();
                    System.out.print("Ingrese autor del libro: ");
                    String autor = scanner.nextLine();
                    System.out.print("Ingrese ISBN del libro: ");
                    String isbn = scanner.nextLine();
                    biblioteca.registrarLibro(new Libro(titulo, autor, isbn));
                    break;

                case 2:
                    System.out.print("Ingrese nombre del usuario: ");
                    String nombre = scanner.nextLine();
                    System.out.print("Ingrese ID del usuario: ");
                    String id = scanner.nextLine();
                    biblioteca.registrarUsuario(new Usuario(nombre, id));
                    break;

                case 3:
                    biblioteca.mostrarCatalogo();
                    break;

                case 4:
                    biblioteca.mostrarUsuarios();
                    break;

                case 5:
                    System.out.print("Ingrese ID del usuario: ");
                    String idUsuarioPrestamo = scanner.nextLine();
                    Usuario usuario = biblioteca.buscarUsuario(idUsuarioPrestamo);
                    if (usuario != null) {
                        System.out.print("Ingrese ISBN del libro: ");
                        String isbnPrestamo = scanner.nextLine();
                        Libro libroPrestamo = biblioteca.buscarLibro(isbnPrestamo);
                        if (libroPrestamo != null) {
                            usuario.prestarLibro(libroPrestamo);
                        } else {
                            System.out.println("Libro no encontrado.");
                        }
                    } else {
                        System.out.println("Usuario no encontrado.");
                    }
                    break;

                case 6:
                    System.out.print("Ingrese ID del usuario: ");
                    String idUsuarioDevolucion = scanner.nextLine();
                    Usuario usuarioDevolucion = biblioteca.buscarUsuario(idUsuarioDevolucion);
                    if (usuarioDevolucion != null) {
                        System.out.print("Ingrese ISBN del libro: ");
                        String isbnDevolucion = scanner.nextLine();
                        Libro libroDevolucion = biblioteca.buscarLibro(isbnDevolucion);
                        if (libroDevolucion != null) {
                            usuarioDevolucion.devolverLibro(libroDevolucion);
                        } else {
                            System.out.println("Libro no encontrado.");
                        }
                    } else {
                        System.out.println("Usuario no encontrado.");
                    }
                    break;

                case 7:
                    System.out.println("¡Gracias por usar la biblioteca interactiva!");
                    scanner.close();
                    return;

                default:
                    System.out.println("Opción no válida. Intente de nuevo.");
            }
        }
    }
}