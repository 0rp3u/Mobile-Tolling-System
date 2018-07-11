package pt.isel.ps.LI61N.g30.server.model.domain

import com.fasterxml.jackson.annotation.JsonIgnore
import org.hibernate.annotations.CreationTimestamp
import java.util.*
import javax.persistence.*
import javax.validation.constraints.NotNull

@Entity
@Table(name="mts_user")
data class User(

        //@JsonIgnore
        @Id @GeneratedValue(strategy = GenerationType.AUTO)
        @Column(name="id")
        var id: Long = -1,

        @NotNull
        @Column(name="login", unique = true, insertable= false, updatable = false)
        val login: String,

        @NotNull
        @Column(name="login", unique = true)
        val name: String?,

        @JsonIgnore
        @NotNull
        @Column
        val password_hash: String,

        @JsonIgnore
        @JoinTable(
                name= "mts_user_roles",
                joinColumns=[JoinColumn(name="user_id", referencedColumnName="id")],
                inverseJoinColumns=[JoinColumn(name="role_id", referencedColumnName="id")]
        )
        @ManyToMany
        val roles: List<Role>,

        @OneToMany(mappedBy = "owner")
        val vehicles: MutableList<Vehicle>,

        @OneToMany
        val tickets: MutableList<Ticket>,

        @Column
        @CreationTimestamp
        val created: Date = Date()
)