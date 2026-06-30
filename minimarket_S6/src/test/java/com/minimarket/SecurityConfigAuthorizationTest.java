package com.minimarket;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.minimarket.controller.InventarioController;
import com.minimarket.controller.ProductoController;
import com.minimarket.controller.VentaController;
import com.minimarket.entity.Inventario;
import com.minimarket.entity.Producto;
import com.minimarket.entity.Venta;
import com.minimarket.security.config.SecurityConfig;
import com.minimarket.security.service.CustomUserDetailsService;
import com.minimarket.service.InventarioService;
import com.minimarket.service.ProductoService;
import com.minimarket.service.VentaService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = {ProductoController.class, InventarioController.class, VentaController.class})
@Import(SecurityConfig.class)
class SecurityConfigAuthorizationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ProductoService productoService;

    @MockBean
    private InventarioService inventarioService;

    @MockBean
    private VentaService ventaService;

    @MockBean
    private CustomUserDetailsService customUserDetailsService;

    @Test
    void actualizarProductoSinAutenticacionRedirigeAlLogin() throws Exception {
        mockMvc.perform(put("/api/productos/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(crearProducto())))
                .andExpect(status().is3xxRedirection());

        verify(productoService, never()).save(any(Producto.class));
    }

    @Test
    @WithMockUser(authorities = "CLIENTE")
    void actualizarProductoConClienteAutenticadoRetornaForbidden() throws Exception {
        mockMvc.perform(put("/api/productos/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(crearProducto())))
                .andExpect(status().isForbidden());

        verify(productoService, never()).save(any(Producto.class));
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    void actualizarProductoConAdministradorPermiteOperacion() throws Exception {
        Producto producto = crearProducto();
        when(productoService.findById(1L)).thenReturn(producto);
        when(productoService.save(any(Producto.class))).thenReturn(producto);

        mockMvc.perform(put("/api/productos/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(producto)))
                .andExpect(status().isOk());

        verify(productoService).save(any(Producto.class));
    }

    @Test
    @WithMockUser(authorities = "CLIENTE")
    void registrarInventarioConClienteAutenticadoRetornaForbidden() throws Exception {
        mockMvc.perform(post("/api/inventario")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(crearInventario())))
                .andExpect(status().isForbidden());

        verify(inventarioService, never()).save(any(Inventario.class));
    }

    @Test
    @WithMockUser(authorities = "CAJERO")
    void registrarInventarioConCajeroPermiteOperacion() throws Exception {
        Inventario inventario = crearInventario();
        when(inventarioService.save(any(Inventario.class))).thenReturn(inventario);

        mockMvc.perform(post("/api/inventario")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inventario)))
                .andExpect(status().isOk());

        verify(inventarioService).save(any(Inventario.class));
    }

    @Test
    @WithMockUser(authorities = "CLIENTE")
    void registrarVentaConClienteAutenticadoRetornaForbidden() throws Exception {
        mockMvc.perform(post("/api/ventas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new Venta())))
                .andExpect(status().isForbidden());

        verify(ventaService, never()).registrarVenta(any(Venta.class));
    }

    @Test
    @WithMockUser(authorities = "CAJERO")
    void registrarVentaConCajeroPermiteOperacion() throws Exception {
        Venta venta = new Venta();
        when(ventaService.registrarVenta(any(Venta.class))).thenReturn(venta);

        mockMvc.perform(post("/api/ventas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(venta)))
                .andExpect(status().isOk());

        verify(ventaService).registrarVenta(any(Venta.class));
    }

    private Producto crearProducto() {
        Producto producto = new Producto();
        producto.setId(1L);
        producto.setNombre("Arroz integral");
        producto.setPrecio(1500.0);
        producto.setStock(20);
        return producto;
    }

    private Inventario crearInventario() {
        Inventario inventario = new Inventario();
        inventario.setId(1L);
        inventario.setProducto(crearProducto());
        inventario.setTipoMovimiento("ENTRADA");
        inventario.setCantidad(10);
        return inventario;
    }
}
