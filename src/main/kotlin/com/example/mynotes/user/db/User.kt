package com.example.mynotes.user.db

import com.example.mynotes.user.UserRole
import com.fasterxml.jackson.annotation.JsonIgnore
import org.hibernate.annotations.CreationTimestamp
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.util.*
import javax.persistence.*

@Entity
data class User(
    @Column(unique = true)
    var name: String,
    @Column(unique = true)
    var email: String,
    @JsonIgnore
    var pswd: String,
    @JsonIgnore
    var enabled: Boolean,
    @JsonIgnore
    var locked: Boolean = false,
    @JsonIgnore
    var role: UserRole = UserRole.USER,
) : UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "serial")
    @JsonIgnore
    var id: Long? = null

    @CreationTimestamp
    @JsonIgnore
    var createdAt: Date? = null


    override fun getAuthorities() = listOf(SimpleGrantedAuthority(role.name))

    override fun getPassword() = pswd

    override fun getUsername() = email

    override fun isAccountNonExpired() = true

    override fun isAccountNonLocked() = !locked

    override fun isCredentialsNonExpired() = true

    override fun isEnabled() = enabled
}
