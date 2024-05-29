import javax.swing.*
import java.awt.*
import java.io.File
import java.io.IOException
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
//  Jair Sanchez
// Definimos la clase Cliente
class Cliente(
    val nombre: String,
    val direccion: String,
    val telefono: String,
    val email: String,
    val fechaRegistro: String = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
) {
    val pedidos = mutableListOf<Pedido>()

    fun agregarPedido(pedido: Pedido) {
        pedidos.add(pedido)
    }

    fun obtenerPedidos(): List<Pedido> {
        return pedidos.toList()
    }

    fun eliminarPedido(index: Int) {
        if (index in 0 until pedidos.size) {
            pedidos.removeAt(index)
        }
    }

    fun editarPedido(index: Int, pedido: Pedido) {
        if (index in 0 until pedidos.size) {
            pedidos[index] = pedido
        }
    }

    fun guardarEnArchivo() {
        try {
            val clienteFile = File("$nombre.txt")
            clienteFile.bufferedWriter().use { out ->
                out.write("Nombre: $nombre\n")
                out.write("Dirección: $direccion\n")
                out.write("Teléfono: $telefono\n")
                out.write("Email: $email\n")
                out.write("Fecha de registro: $fechaRegistro\n")
            }

            val pedidosFile = File("$nombre-pedidos.txt")
            pedidosFile.bufferedWriter().use { out ->
                pedidos.forEachIndexed { index, pedido ->
                    out.write("Pedido ${index + 1}:\n")
                    out.write("Fecha: ${pedido.fechaPedido}\n")
                    out.write("Estado: ${pedido.estado}\n")
                    out.write("Total: ${pedido.total}\n")
                    out.write("Método de pago: ${pedido.metodoPago}\n")
                }
            }
            println("Información del cliente guardada en archivos.")
        } catch (e: IOException) {
            println("Error al guardar la información en archivos.")
            e.printStackTrace()
        }
    }
}

class Pedido(
    var fechaPedido: String = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
    var estado: String = "Pendiente",
    val total: Double,
    val metodoPago: String
)

fun main() {
    SwingUtilities.invokeLater {
        val cliente = Cliente("Jair", "Calle Principal 123", "0987654321", "juan@example.com")
        MainFrame(cliente).isVisible = true
    }
}

class MainFrame(val cliente: Cliente) : JFrame() {
    init {
        title = "Sistema de Gestión de Pedidos"
        setSize(600, 400)
        defaultCloseOperation = EXIT_ON_CLOSE
        setLocationRelativeTo(null)
        layout = BorderLayout()

        val panel = JPanel()
        panel.layout = GridLayout(5, 1)

        val buttonCrearPedido = JButton("Crear Pedido")
        val buttonVerPedidos = JButton("Ver Pedidos")
        val buttonEditarPedido = JButton("Editar Pedido")
        val buttonEliminarPedido = JButton("Eliminar Pedido")
        val buttonSalir = JButton("Salir")

        panel.add(buttonCrearPedido)
        panel.add(buttonVerPedidos)
        panel.add(buttonEditarPedido)
        panel.add(buttonEliminarPedido)
        panel.add(buttonSalir)

        buttonCrearPedido.addActionListener { crearPedido() }
        buttonVerPedidos.addActionListener { verPedidos() }
        buttonEditarPedido.addActionListener { editarPedido() }
        buttonEliminarPedido.addActionListener { eliminarPedido() }
        buttonSalir.addActionListener { guardarYSalir() }

        add(panel, BorderLayout.CENTER)


        panel.background = Color(50, 50, 50)
        buttonCrearPedido.background = Color(100, 200, 100)
        buttonVerPedidos.background = Color(100, 150, 250)
        buttonEditarPedido.background = Color(250, 200, 100)
        buttonEliminarPedido.background = Color(250, 100, 100)
        buttonSalir.background = Color(200, 200, 200)

        buttonCrearPedido.foreground = Color.WHITE
        buttonVerPedidos.foreground = Color.WHITE
        buttonEditarPedido.foreground = Color.BLACK
        buttonEliminarPedido.foreground = Color.WHITE
        buttonSalir.foreground = Color.BLACK
    }

    private fun crearPedido() {
        val totalStr = JOptionPane.showInputDialog(this, "Introduce el total del pedido:")
        val metodoPago = JOptionPane.showInputDialog(this, "Introduce el método de pago:")

        if (totalStr != null && metodoPago != null) {
            val total = totalStr.toDoubleOrNull() ?: 0.0
            val pedido = Pedido(total = total, metodoPago = metodoPago)
            cliente.agregarPedido(pedido)
            JOptionPane.showMessageDialog(this, "Pedido creado correctamente.")
        } else {
            JOptionPane.showMessageDialog(this, "Operación cancelada.")
        }
    }

    private fun verPedidos() {
        val sb = StringBuilder()
        sb.append("Pedidos de ${cliente.nombre}:\n")
        cliente.obtenerPedidos().forEachIndexed { index, pedido ->
            sb.append("Pedido ${index + 1}:\n")
            sb.append("Fecha: ${pedido.fechaPedido}\n")
            sb.append("Estado: ${pedido.estado}\n")
            sb.append("Total: ${pedido.total}\n")
            sb.append("Método de pago: ${pedido.metodoPago}\n")
        }
        JOptionPane.showMessageDialog(this, sb.toString())
    }

    private fun editarPedido() {
        if (cliente.pedidos.isNotEmpty()) {
            val sb = StringBuilder()
            sb.append("Elige el pedido a editar:\n")
            cliente.obtenerPedidos().forEachIndexed { index, _ ->
                sb.append("${index + 1}) Pedido ${index + 1}\n")
            }
            val indexStr = JOptionPane.showInputDialog(this, sb.toString())
            val index = indexStr?.toIntOrNull()?.minus(1)
            if (index != null && index in 0 until cliente.pedidos.size) {
                val pedido = cliente.pedidos[index]
                val nuevaFecha = JOptionPane.showInputDialog(this, "Introduce la nueva fecha del pedido (yyyy-MM-dd HH:mm:ss):", pedido.fechaPedido)
                val nuevoEstado = JOptionPane.showInputDialog(this, "Introduce el nuevo estado del pedido:", pedido.estado)

                if (nuevaFecha != null && nuevoEstado != null) {
                    cliente.editarPedido(
                        index,
                        Pedido(
                            fechaPedido = nuevaFecha,
                            estado = nuevoEstado,
                            total = pedido.total,
                            metodoPago = pedido.metodoPago
                        )
                    )
                    JOptionPane.showMessageDialog(this, "Pedido editado correctamente.")
                } else {
                    JOptionPane.showMessageDialog(this, "Operación cancelada.")
                }
            } else {
                JOptionPane.showMessageDialog(this, "Índice inválido.")
            }
        } else {
            JOptionPane.showMessageDialog(this, "No hay pedidos para editar.")
        }
    }

    private fun eliminarPedido() {
        if (cliente.pedidos.isNotEmpty()) {
            val sb = StringBuilder()
            sb.append("Elige el pedido a eliminar:\n")
            cliente.obtenerPedidos().forEachIndexed { index, _ ->
                sb.append("${index + 1}) Pedido ${index + 1}\n")
            }
            val indexStr = JOptionPane.showInputDialog(this, sb.toString())
            val index = indexStr?.toIntOrNull()?.minus(1)
            if (index != null && index in 0 until cliente.pedidos.size) {
                cliente.eliminarPedido(index)
                JOptionPane.showMessageDialog(this, "Pedido eliminado correctamente.")
            } else {
                JOptionPane.showMessageDialog(this, "Índice inválido.")
            }
        } else {
            JOptionPane.showMessageDialog(this, "No hay pedidos para eliminar.")
        }
    }

    private fun guardarYSalir() {
        cliente.guardarEnArchivo()
        JOptionPane.showMessageDialog(this, "¡Hasta luego!")
        dispose()
    }
}
