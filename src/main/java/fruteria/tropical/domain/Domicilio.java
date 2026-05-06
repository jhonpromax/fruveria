package fruteria.tropical.domain;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "domicilios")
public class Domicilio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String direccion;
    private String barrio;
    private String telefono;
    private String estado; // PENDIENTE, EN_CAMINO, ENTREGADO, CANCELADO
    private LocalDate fechaEntrega;
    private Double costoEnvio;

    // ENLACE CON EL PEDIDO
    @ManyToOne
    @JoinColumn(name = "order_id") // Llave foránea en la tabla domicilios
    private Order order;

    // ENLACE CON EL CLIENTE
    @ManyToOne
    @JoinColumn(name = "customer_id") // Llave foránea en la tabla domicilios
    private Customer customer;

    public Domicilio() {}

    // --- GETTERS Y SETTERS ---
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }

    public String getBarrio() { return barrio; }
    public void setBarrio(String barrio) { this.barrio = barrio; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public LocalDate getFechaEntrega() { return fechaEntrega; }
    public void setFechaEntrega(LocalDate fechaEntrega) { this.fechaEntrega = fechaEntrega; }

    public Double getCostoEnvio() { return costoEnvio; }
    public void setCostoEnvio(Double costoEnvio) { this.costoEnvio = costoEnvio; }

    public Order getOrder() { return order; }
    public void setOrder(Order order) { this.order = order; }

    public Customer getCustomer() { return customer; }
    public void setCustomer(Customer customer) { this.customer = customer; }
}
