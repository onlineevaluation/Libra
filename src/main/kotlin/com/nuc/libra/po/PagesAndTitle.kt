package com.nuc.libra.po

import javax.persistence.*

/**
 * @author 杨晓辉 2018/2/3 11:08
 */
@Entity
@Table(name = "uek_evaluate_pages_title")
class PagesAndTitle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0L

    @Column(name = "pages_id")
    var pagesId: Long = 0L

    @Column(name = "title_id")
    var titleId: Long = 0L

}