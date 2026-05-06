package fruteria.tropical.domain;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "pick_ups")
public class PickUp {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String estado; // PENDIENTE, PREPARANDO, LISTO, ENTREGADO
    private String metodoPago; // EFECTIVO, TRANSFERENCIA
    private String estadoPago; // PENDIENTE, PAGADO
    private LocalDateTime horaEstimadaRecogida;
    private String nombreCaja; // Identificador de la caja donde está listo el pedido

    // ENLACE CON EL PEDIDO
    @ManyToOne
    @JoinColumn(name = "order_id") // Llave foránea en la tabla pick_ups
    private Order order;

    // ENLACE CON EL CLIENTE
    @ManyToOne
    @JoinColumn(name = "customer_id") // Llave foránea en la tabla pick_ups
    private Customer customer;

    public PickUp() {}

    // --- GETTERS Y SETTERS ---
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public String getMetodoPago() { return metodoPago; }
    public void setMetodoPago(String metodoPago) { this.metodoPago = metodoPago; }

    public String getEstadoPago() { return estadoPago; }
    public void setEstadoPago(String estadoPago) { this.estadoPago = estadoPago; }

    public LocalDateTime getHoraEstimadaRecogida() { return horaEstimadaRecogida; }
    public void setHoraEstimadaRecogida(LocalDateTime horaEstimadaRecogida) { this.horaEstimadaRecogida = horaEstimadaRecogida; }

    public String getNombreCaja() { return nombreCaja; }
    public void setNombreCaja(String nombreCaja) { this.nombreCaja = nombreCaja; }

    public Order getOrder() { return order; }
    public void setOrder(Order order) { this.order = order; }

    public Customer getCustomer() { return customer; }
    public void setCustomer(Customer customer) { this.customer = customer; }
}
