package com.cursoandroid.app10listadetarefas.helper;

import com.cursoandroid.app10listadetarefas.model.Tarefa;

import java.util.List;

public interface ITarefaDAO {
    boolean salvar(Tarefa tarefa);
    boolean atualizar(Tarefa tarefa);
    boolean excluir(Tarefa tarefa);
    List<Tarefa> listar();
}
