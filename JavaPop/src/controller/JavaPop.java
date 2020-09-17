/*
 * To change this license header, choose License Headers in Project Properties. To change this
 * template file, choose Tools | Templates and open the template in the editor.
 */
package controller;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import model.Administrador;
import model.Categoria;
import model.Cliente;
import model.ClienteProfesional;
import model.Compra;
import model.EstadoProducto;
import model.Horario;
import model.Producto;
import model.Ubicacion;

public class JavaPop {

    private static List<Cliente> clientes;
    private static List<Producto> productos;
    private static List<Compra> compras;

    public static void main(String[] args) throws ClassNotFoundException {

        init();

        Scanner scanner = new Scanner(System.in);
        boolean exit = false;
        boolean isAuthenticated = false;
        boolean isAdmin = false;
        Cliente cliente = null;

        while (!exit) {
            while (!isAuthenticated && !exit) {

                printInitialMenu();
                int comando = Integer.valueOf(scanner.nextLine());

                switch (comando) {
                    case 1:
                        System.out.println("Introduce tu correo:");
                        String correo = scanner.nextLine();

                        System.out.println("Introduce tu clave:");
                        String clave = scanner.nextLine();

                        cliente = login(correo, clave);
                        if (cliente == null) {
                            isAdmin = esAdmin(correo, clave);
                            isAuthenticated = isAdmin;
                        } else {
                            isAuthenticated = true;
                        }
                        break;
                    case 2:
                        Cliente nuevoCliente = leerCliente(scanner);
                        if (añadirCliente(nuevoCliente)) {
                            System.out.println("Cliente registrado");
                        } else {
                            System.err.println("Ese cliente ya existe");
                        }
                        break;
                    case 3:
                        exit = true;
                        try {
                            guardar();
                        } catch (IOException e) {
                            System.err.println("Error al guardar los datos");
                        }
                }
            }

            if (!exit) {
                if (isAdmin) {

                    printAdminMenu();
                    int comando = Integer.valueOf(scanner.nextLine());

                    switch (comando) {
                        case 1:
                            consultarUsuarios();
                            break;
                        case 2:
                            consultarProductos();
                            break;
                        case 3:
                            consultarVentas();
                            break;
                        case 4:
                            darBajaCliente(scanner);
                            break;
                        case 5:
                            darBajaProducto(scanner);
                            break;
                        case 6:
                            isAuthenticated = false;
                            break;
                        case 7:
                            exit = true;
                            try {
                                guardar();
                            } catch (IOException e) {
                                e.printStackTrace();
                                System.err.println("Error al guardar los datos");
                            }
                            break;
                        default:
                            break;
                    }
                } else if (esProfesional(cliente)) {
                    printProfesionalMenu();
                    int comando = Integer.valueOf(scanner.nextLine());

                    switch (comando) {
                        case 1:
                            System.out.println("Se le ha hecho un cargo en su tarjeta de crédito ");
                            break;
                        case 2:
                            altaProducto(cliente, scanner);
                            System.out.println("Producto añadido correctamente");
                            System.out.println();
                            System.out.println();
                            break;
                        case 3:
                            borrarProductos(cliente);
                            System.out.println("Productos borrados");
                            System.out.println();
                            System.out.println();
                            break;
                        case 4:
                            System.out.println("Introduce la categoria:");
                            for (Categoria cat : Categoria.values()) {
                                System.out.println(cat.name());
                            }

                            Categoria categoria = Categoria.valueOf(scanner.nextLine());

                            System.out.println(
                                    "Introducir palabras clave del producto a buscar separadas por un espacio (vacio si no quieres): ");

                            String palabrasClave = scanner.nextLine();

                            List<Producto> productosEncontrados = buscarProductos(categoria, palabrasClave);
                            for (Producto producto : productosEncontrados) {
                                System.out.println(producto);
                            }
                            break;
                        case 5:
                            System.out.println("Introduce id del producto a comprar:");
                            int id = Integer.valueOf(scanner.nextLine());
                            comprarProducto(cliente, id);
                            System.out.println("Producto marcado para comprar. Espera la confirmación del vendedor");
                            System.out.println();
                            System.out.println();
                            break;
                        case 6:
                            revisarCompras(cliente);
                            System.out.println("Comprar verificadas");
                            System.out.println();
                            System.out.println();
                            break;
                        case 7:
                            isAuthenticated = false;
                            break;
                        case 8:
                            exit = true;
                            try {
                                guardar();
                            } catch (IOException e) {
                                e.printStackTrace();
                                System.err.println("Error al guardar los datos");
                            }
                            break;
                        default:
                            break;
                    }
                } else {

                    printClientMenu();
                    int comando = Integer.valueOf(scanner.nextLine());

                    switch (comando) {
                        case 1:
                            ClienteProfesional nuevoCliente = leerDatosAdicionales(cliente, scanner);
                            int i;
                            boolean encontrado = false;
                            for (i = 0; i < clientes.size() && !encontrado; i++) {
                                encontrado = clientes.get(i).getDni().equals(cliente.getDni());
                            }

                            clientes.set(i - 1, nuevoCliente);

                            cliente = nuevoCliente;

                            break;
                        case 2:
                            Producto producto = leerDatosProducto(cliente, scanner);
                            productos.add(producto);
                            System.out.println("Producto añadido correctamente");
                            System.out.println();
                            System.out.println();
                            break;
                        case 3:
                            borrarProductos(cliente);
                            System.out.println("Productos borrados");
                            System.out.println();
                            System.out.println();
                            break;
                        case 4:
                            System.out.println("Introduce la categoria:");
                            for (Categoria cat : Categoria.values()) {
                                System.out.println(cat.name());
                            }

                            Categoria categoria = Categoria.valueOf(scanner.nextLine());

                            System.out.println(
                                    "Introducir palabras clave del producto a buscar separadas por un espacio (vacio si no quieres): ");

                            String palabrasClave = scanner.nextLine();

                            List<Producto> productosEncontrados = buscarProductos(categoria, palabrasClave);
                            for (Producto productoEncontrado : productosEncontrados) {
                                System.out.println(productoEncontrado);
                            }
                            break;
                        case 5:
                            System.out.println("Introduce id del producto a comprar:");
                            int id = Integer.valueOf(scanner.nextLine());
                            comprarProducto(cliente, id);
                            System.out.println("Producto marcado para comprar. Espera la confirmación del vendedor");
                            System.out.println();
                            System.out.println();
                            break;
                        case 6:
                            revisarCompras(cliente);
                            System.out.println("Compra verificada");
                            System.out.println();
                            System.out.println();
                            break;
                        case 7:
                            isAuthenticated = false;
                            break;
                        case 8:
                            exit = true;
                            try {
                                guardar();
                            } catch (IOException e) {
                                e.printStackTrace();
                                System.err.println("Error al guardar los datos");
                            }
                            break;
                        default:
                            break;
                    }

                }
            }
        }

        scanner.close();

    }

    private static void revisarCompras(Cliente cliente) {
        for (int i = 0; i < compras.size(); i++) {
            Compra compra = compras.get(i);
            if (compra.getDniVendedor().equals(cliente.getDni())) {
                compra.setConfirmada(true);
                productos.remove(compra.getProducto());
                try {
                    generarFichero(compra);
                } catch (IOException e) {
                    System.err.println("Error al generar factura");
                }
            }
        }
    }

    private static void generarFichero(Compra compra) throws IOException {
        String fileName = "factura" + LocalDateTime.now() + ".txt";
        FileOutputStream fis = new FileOutputStream(fileName);
        fis.write(compra.toString().getBytes());
        fis.close();
    }

    private static Cliente login(String correo, String clave) {

        return comprobarCredenciales(correo, clave);
    }

    private static void comprarProducto(Cliente cliente, int id) {
        Producto productoComprar = obtenerProductoId(id);
        Compra compra = new Compra();
        compra.setFecha(LocalDate.now());
        compra.setProducto(productoComprar);
        compra.setNombreComprador(cliente.getNombre());
        compra.setDniComprador(cliente.getDni());
        compra.setDniVendedor(productoComprar.getAñadidoPor());

        for (int i = 0; i < clientes.size(); i++) {
            if (clientes.get(i).getDni().equals(productoComprar.getAñadidoPor())) {
                compra.setNombreVendedor(clientes.get(i).getNombre());
                break;
            }
        }

        compras.add(compra);
    }

    private static void altaProducto(Cliente cliente, Scanner scanner) {
        Producto producto = leerDatosProducto(cliente, scanner);
        productos.add(producto);
    }

    private static void printInitialMenu() {
        System.out.println("\033[36m¡Bienvenido a JAVAPOP!");
        System.out.println("Elige una opcion:");
        System.out.println("1 - Iniciar sesión");
        System.out.println("2 - Registrarse");
        System.out.println("3 - Exit");
    }

    private static void printAdminMenu() {
        System.out.println("\033[36m Iniciada sesión como administrador");
        System.out.println("Elige una opcion:");
        System.out.println("1 - Consultar usuarios");
        System.out.println("2 - Consultar productos");
        System.out.println("3 - Consultar ventas realizadas");
        System.out.println("4 - Dar baja cliente fraudulento");
        System.out.println("5 - Dar baja producto fraudulento");
        System.out.println("6 - Cerrar sesion");
        System.out.println("7 - Exit");
    }

    private static void printProfesionalMenu() {
        System.out.println("\033[35m Iniciada sesion como cliente profesional");
        System.out.println("Elige una opcion:");
        System.out.println("1 - Comprobar estado pago cuota 30€");
        System.out.println("2 - Alta producto");
        System.out.println("3 - Baja productos de un usuario");
        System.out.println("4 - Buscar productos");
        System.out.println("5 - Comprar productos");
        System.out.println("6 - Confirmar compra");
        System.out.println("7 - Cerrar sesion");
        System.out.println("8 - Exit");
    }

    private static void printClientMenu() {
        System.out.println("\033[34m Iniciada sesion como cliente");
        System.out.println("Elige una opcion:");
        System.out.println("1 - Ser cliente profesional");
        System.out.println("2 - Alta producto");
        System.out.println("3 - Baja productos de un usuario");
        System.out.println("4 - Buscar productos");
        System.out.println("5 - Comprar productos");
        System.out.println("6 - Confirmar compra");
        System.out.println("7 - Cerrar sesion");
        System.out.println("8 - Exit");
    }

    private static void init() throws ClassNotFoundException {
        try {
            clientes = leerClientes();
        } catch (IOException e) {
            clientes = new ArrayList<>();
            Cliente cliente1 = new Cliente("12345671", "Paco", "correo1@correo.com", "clave",
                    new Ubicacion("28027", "Madrid"), "1234567891234561");
            Cliente cliente2 = new Cliente("12345672", "Jorge", "correo2@correo.com", "clave",
                    new Ubicacion("28028", "Barcelona"), "1234567891234562");
            Cliente cliente3 = new Cliente("12345673", "Pedro", "correo3@correo.com", "clave",
                    new Ubicacion("28028", "Barcelona"), "1234567891234563");
            Cliente cliente4 = new Cliente("12345674", "Maria", "correo4@correo.com", "clave",
                    new Ubicacion("28028", "Barcelona"), "1234567891234564");
            Cliente cliente5 = new Cliente("12345675", "Laura", "correo5@correo.com", "clave",
                    new Ubicacion("28028", "Barcelona"), "1234567891234565");
            Cliente cliente6 = new ClienteProfesional("12345670", "PCShop", "tiendapc@correo.com", "clave",
                    new Ubicacion("28027", "Madrid"), "1234567891234569", "Tienda ordenadores",
                    new Horario(LocalTime.of(8, 0), LocalTime.of(18, 0)), "922222222", "tiendapc.com");
            Cliente cliente7 = new ClienteProfesional("12345679", "ZapatillaShop", "tiendazapatillas@correo.com",
                    "clave", new Ubicacion("28027", "Madrid"), "1234567891234561", "Tienda zapatillas",
                    new Horario(LocalTime.of(8, 0), LocalTime.of(18, 0)), "911111111", "tiendazapatillas.com");

            clientes.add(cliente1);
            clientes.add(cliente2);
            clientes.add(cliente3);
            clientes.add(cliente4);
            clientes.add(cliente5);
            clientes.add(cliente6);
            clientes.add(cliente7);
        }
        try {
            productos = leerProductos();
        } catch (IOException e) {
            productos = new ArrayList<>();
            Producto producto1 = new Producto(1, "Zapatillas Nike", "zapatillas de futbol", Categoria.DEPORTES_OCIO,
                    EstadoProducto.NUEVO, 120.50, LocalDate.now(), new Ubicacion("28027", "Madrid"), "12345678");
            Producto producto2 = new Producto(2, "Fornite shooter", "videojuego", Categoria.CONSOLAS_VIDEOJUEGOS,
                    EstadoProducto.COMO_NUEVO, 60.50, LocalDate.now(), new Ubicacion("28028", "Barcelona"), "12345678");
            Producto producto3 = new Producto(3, "Zapatillas Adidas", "zapatillas de hockey", Categoria.DEPORTES_OCIO,
                    EstadoProducto.NUEVO, 115.50, LocalDate.now(), new Ubicacion("28027", "Madrid"), "12345678");
            Producto producto4 = new Producto(4, "Zapatillas Reebok", "zapatillas de padel", Categoria.DEPORTES_OCIO,
                    EstadoProducto.NUEVO, 90.50, LocalDate.now(), new Ubicacion("28027", "Madrid"), "12345678");
            Producto producto5 = new Producto(5, "Monitor Dell", "monitor para PC", Categoria.INFORMATICA_ELECTRONICA,
                    EstadoProducto.ACEPTABLE, 70.35, LocalDate.now(), new Ubicacion("28027", "Madrid"), "12345678");
            Producto producto6 = new Producto(6, "Raton Logitech", "raton para jugar",
                    Categoria.INFORMATICA_ELECTRONICA, EstadoProducto.BUENO, 20.20, LocalDate.now(),
                    new Ubicacion("28028", "Barcelona"), "12345678");
            Producto producto7 = new Producto(7, "Pendientes Tous", "pendientes de fiesta", Categoria.MODA_ACCESORIOS,
                    EstadoProducto.NUEVO, 60.50, LocalDate.now(), new Ubicacion("28028", "Barcelona"), "12345672");
            Producto producto8 = new Producto(8, "Smartphone Samsung", "smartphone ultima generacion",
                    Categoria.MOVILES_TELEFONIA, EstadoProducto.REGULAR, 230.50, LocalDate.now(),
                    new Ubicacion("28028", "Barcelona"), "12345678");
            Producto producto9 = new Producto(9, "Smart TV Sony", "Television 4K Sony", Categoria.TV_AUDIO_FOTO,
                    EstadoProducto.NUEVO, 700.50, LocalDate.now(), new Ubicacion("28028", "Barcelona"), "12345678");
            Producto producto10 = new Producto(10, "Smart TV LG", "Television 8K LG", Categoria.TV_AUDIO_FOTO,
                    EstadoProducto.NUEVO, 1000.50, LocalDate.now(), new Ubicacion("28028", "Barcelona"), "12345678");

            productos.add(producto1);
            productos.add(producto2);
            productos.add(producto3);
            productos.add(producto4);
            productos.add(producto5);
            productos.add(producto6);
            productos.add(producto7);
            productos.add(producto8);
            productos.add(producto9);
            productos.add(producto10);

        }
        try {
            compras = leerCompras();
        } catch (IOException e) {
            compras = new ArrayList<>();
        }

    }

    private static Producto obtenerProductoId(int id) {
        int i;
        for (i = 0; i < productos.size(); i++) {
            if (productos.get(i).getId() == id) {
                return productos.get(i);
            }
        }

        return null;
    }

    private static List<Producto> buscarProductos(Categoria categoria, String palabrasClave) {

        List<Producto> productosEncontrados = new ArrayList<>();

        if (!palabrasClave.isEmpty()) {
            String[] palabras = palabrasClave.split(" ");

            for (Producto producto : productos) {
                if (producto.getCategoria().equals(categoria)) {
                    int k;
                    for (k = 0; k < palabras.length; k++) {
                        if (producto.getTitulo().contains(palabras[k])) {
                            productosEncontrados.add(producto);
                            break;
                        }
                    }
                }
            }
        } else {
            for (Producto producto : productos) {
                if (producto.getCategoria().equals(categoria)) {
                    productosEncontrados.add(producto);
                }
            }
        }

        // ordenar por proximidad
        // ordenar por urgentes
        return productosEncontrados;

    }

    private static void borrarProductos(Cliente cliente) {
        int j;
        List<Integer> productosABorrar = new ArrayList<>();
        for (j = 0; j < productos.size(); j++) {
            if (productos.get(j).getAñadidoPor().equals(cliente.getDni())) {
                productosABorrar.add(j);
            }
        }

        for (int index : productosABorrar) {
            productos.remove(index);
        }
    }

    private static Producto leerDatosProducto(Cliente cliente, Scanner scanner) {

        Producto producto = new Producto();

        System.out.println("Introduce la categoria del producto:");
        for (Categoria cat : Categoria.values()) {
            System.out.println(cat.name());
        }
        Categoria categoria = Categoria.valueOf(scanner.nextLine());
        producto.setCategoria(categoria);

        System.out.println("Introduce la descripción del producto:");
        String descripcion = scanner.nextLine();
        producto.setDescripcion(descripcion);

        System.out.println("Introduce el estado del producto:");
        for (EstadoProducto estado : EstadoProducto.values()) {
            System.out.println(estado.name());
        }
        EstadoProducto estado = EstadoProducto.valueOf(scanner.nextLine());

        producto.setEstado(estado);

        LocalDate fecha = LocalDate.now();
        producto.setFecha(fecha);

        System.out.println("Introduce el precio del producto:");
        double precio = Double.valueOf(scanner.nextLine());
        producto.setPrecio(precio);

        System.out.println("Introduce el titulo del producto:");
        String titulo = scanner.nextLine();
        producto.setTitulo(titulo);

        producto.setUbicacion(cliente.getUbicacion());

        System.out.println("Indica si el producto es urgente (0) o no lo es (1):");
        boolean urgente = scanner.nextLine().equals("0") ? true : false;
        producto.setUrgente(urgente);
        return producto;

    }

    private static boolean esProfesional(Cliente cliente) {
        return cliente instanceof ClienteProfesional;
    }

    private static boolean añadirCliente(Cliente cliente) {

        int i;
        for (i = 0; i < clientes.size(); i++) {
            if (clientes.get(i).getDni().equals(cliente.getDni())
                    || clientes.get(i).getCorreo().equals(cliente.getCorreo())) {
                break;
            }
        }

        if (i == clientes.size()) {
            clientes.add(cliente);
            return true;
        }

        return false;

    }

    private static Cliente comprobarCredenciales(String correo, String clave) {

        for (int i = 0; i < clientes.size(); i++) {
            if (clientes.get(i).getCorreo().equals(correo) && clientes.get(i).getClave().equals(clave)) {
                return clientes.get(i);
            }
        }

        return null;

    }

    private static boolean esAdmin(String correo, String clave) {
        return correo.equals(Administrador.CORREO) && clave.equals(Administrador.CLAVE);
    }

    private static Cliente leerCliente(Scanner scanner) {

        Cliente cliente = new Cliente();

        System.out.println("Introduce el dni del cliente:");
        String dni = scanner.nextLine();
        cliente.setDni(dni);

        System.out.println("Introduce el nombre del cliente:");
        String nombre = scanner.nextLine();
        cliente.setNombre(nombre);

        System.out.println("Introduce el codigo postal del cliente:");
        Ubicacion ubicacion = new Ubicacion();
        String codigoPostal = scanner.nextLine();
        ubicacion.setCodigoPostal(codigoPostal);
        System.out.println("Introduce la ciudad del cliente:");
        String ciudad = scanner.nextLine();
        ubicacion.setCiudad(ciudad);
        cliente.setUbicacion(ubicacion);

        System.out.println("Introduce la tarjeta del cliente:");
        String tarjeta = scanner.nextLine();
        cliente.setTarjeta(tarjeta);

        System.out.println("Introduce el correo del cliente:");
        String correo = scanner.nextLine();
        cliente.setCorreo(correo);

        System.out.println("Introduce la clave del cliente");
        String clave = scanner.nextLine();
        cliente.setClave(clave);

        return cliente;
    }

    private static ClienteProfesional leerDatosAdicionales(Cliente cliente, Scanner scanner) {

        ClienteProfesional nuevoCliente = new ClienteProfesional();
        nuevoCliente.setDni(cliente.getDni());
        nuevoCliente.setNombre(cliente.getNombre());
        nuevoCliente.setUbicacion(cliente.getUbicacion());
        nuevoCliente.setTarjeta(cliente.getTarjeta());
        nuevoCliente.setCorreo(cliente.getCorreo());
        nuevoCliente.setClave(cliente.getClave());

        System.out.println("Introduce la descripción de la tienda:");
        String descripcion = scanner.nextLine();
        nuevoCliente.setDescripcion(descripcion);

        System.out.println("Introduce el teléfono de la tienda:");
        String telefono = scanner.nextLine();
        nuevoCliente.setTelefono(telefono);

        System.out.println("Introduce la web de la tienda:");
        String web = scanner.nextLine();
        nuevoCliente.setWeb(web);

        Horario horario = new Horario();

        System.out.println("Introduce la hora de apertura de la tienda:");
        String horaApertura = scanner.nextLine();
        horario.setApertura(LocalTime.parse(horaApertura));

        System.out.println("Introduce la hora de cierre de la tienda:");
        String horaCierre = scanner.nextLine();
        horario.setCierre(LocalTime.parse(horaCierre));

        nuevoCliente.setHorario(horario);

        return nuevoCliente;
    }

    private static void consultarUsuarios() {

        for (Cliente cliente : clientes) {
            System.out.println(cliente);
        }
        System.out.println();

    }

    private static boolean darBajaCliente(Scanner scanner) {

        System.out.println("Introduce el dni del cliente:");
        String dni = scanner.nextLine();

        int i;
        boolean encontrado = false;
        for (i = 0; i < clientes.size() && !encontrado; i++) {
            if (clientes.get(i).getDni().equals(dni)) {
                encontrado = true;
            }
        }

        if (encontrado) {
            clientes.remove(i - 1);
            System.out.println("El cliente ha sido dado de baja");
            return true;
        } else {
            System.out.println("El cliente no ha sido encontrado");
            return false;
        }
    }

    private static void consultarProductos() {
        for (Producto producto : productos) {
            System.out.println(producto);
        }
    }

    private static boolean darBajaProducto(Scanner scanner) {

        System.out.println("Introduce el nombre del producto:");
        String titulo = scanner.nextLine();

        int i;
        boolean encontrado = false;
        for (i = 0; i < productos.size() && !encontrado; i++) {
            if (productos.get(i).getTitulo().contains(titulo)) {
                encontrado = true;
            }
        }

        if (encontrado) {
            productos.remove(i - 1);
            System.out.println("El producto ha sido dado de baja");
            return true;
        } else {
            System.out.println("El produto no ha sido encontrado");
            return false;
        }
    }

    private static void consultarVentas() {

    }

    private static void guardar() throws IOException {

        // Guardar los clientes
        String fileName = "clientes.txt";
        FileOutputStream fos = new FileOutputStream(fileName);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(clientes);
        oos.close();

        // Guardar los productos
        fileName = "productos.txt";
        fos = new FileOutputStream(fileName);
        oos = new ObjectOutputStream(fos);
        oos.writeObject(productos);
        oos.close();

        // Guardar las compras
        fileName = "compras.txt";
        fos = new FileOutputStream(fileName);
        oos = new ObjectOutputStream(fos);
        oos.writeObject(compras);
        oos.close();
    }

    private static List<Cliente> leerClientes() throws IOException, ClassNotFoundException {

        // Leer los clientes
        String fileName = "clientes.txt";
        FileInputStream fis = new FileInputStream(fileName);
        ObjectInputStream ois = new ObjectInputStream(fis);
        List<Cliente> clientes = (List<Cliente>) ois.readObject();
        ois.close();
        return clientes;
    }

    private static List<Producto> leerProductos() throws ClassNotFoundException, IOException {

        // Leer los productos
        String fileName = "productos.txt";
        FileInputStream fis = new FileInputStream(fileName);
        ObjectInputStream ois = new ObjectInputStream(fis);
        List<Producto> productos = (List<Producto>) ois.readObject();
        ois.close();
        return productos;
    }

    private static List<Compra> leerCompras() throws ClassNotFoundException, IOException {

        // Leer las compras
        String fileName = "compras.txt";
        FileInputStream fis = new FileInputStream(fileName);
        ObjectInputStream ois = new ObjectInputStream(fis);
        List<Compra> compras = (List<Compra>) ois.readObject();
        ois.close();
        return compras;
    }

}
