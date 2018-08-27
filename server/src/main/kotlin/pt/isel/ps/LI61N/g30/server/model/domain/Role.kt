package pt.isel.ps.LI61N.g30.server.model.domain

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name="mts_role")
data class Role(
        @Id
        @Column(name="id")
        val name: String
)