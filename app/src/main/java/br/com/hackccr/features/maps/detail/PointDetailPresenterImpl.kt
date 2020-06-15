package br.com.hackccr.features.maps.detail

import br.com.hackccr.data.Comment

class PointDetailPresenterImpl(private val view: PointDetailView) : PointDetailPresenter {

    override fun onCreate() {
        val comments = mutableListOf<Comment>()
        comments.add(Comment("Muito bom", 5, "GilsonCaminhao", "Comida boa, banheiro limpo e preço camarada.", "2 dias atrás"))
        comments.add(Comment("Lugar ok", 3, "Roberto Caminhoneiro", "Comida boa, banheiro um pouco sujo.", "4 dias atrás"))
        comments.add(Comment("Recomendo", 4, "Joao Silva", "Lugar para descanso e seguro. Comida bem servida", "1 semana atrás"))
        comments.add(Comment("Péssimo", 1, "Vitor do Caminhao", "Sujo e inseguro", "1 mês atrás"))

        view.configureComments(comments)
    }
}