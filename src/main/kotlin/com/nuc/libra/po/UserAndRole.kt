package com.nuc.libra.po

import javax.persistence.*

/**
 * @author 杨晓辉 2018/2/1 16:32
 */
@Entity
@Table(name = "uek_privilege_user_role")
class UserAndRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0L

    @Column(name = "user_id")
    var userId: Long = 0L

    @Column(name = "role_id")
    var roleId: Long = 0L

}