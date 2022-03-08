package com.cursoandroid.app10listadetarefas.adapter;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;
import com.cursoandroid.app10listadetarefas.R;
import com.cursoandroid.app10listadetarefas.helper.TarefaDAO;
import com.cursoandroid.app10listadetarefas.model.Tarefa;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class TarefaAdapter extends RecyclerView.Adapter<TarefaAdapter.MyViewHolder> {
    private List<Tarefa> tarefaList;
    public TarefaAdapter(List<Tarefa> tarefaList) {
        this.tarefaList = tarefaList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.lista_tarefa, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        final Tarefa tarefa = tarefaList.get(position);
        holder.tarefa.setText(tarefa.getNomeTarefa());
        holder.shareButton.setOnClickListener(v->{
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("plain/text");
            intent.putExtra(Intent.EXTRA_TEXT,tarefa.getNomeTarefa());
            v.getContext().startActivity(Intent.createChooser(intent,v.getResources().getString(R.string.share)));
        });
        holder.itemView.setOnClickListener(view -> {
            //Enviar para tela adicionar tarefa
            Bundle bundle = new Bundle();
            bundle.putSerializable("tarefaSelecionada",tarefa);
            Navigation.findNavController(view).navigate(R.id.action_FirstFragment_to_SecondFragment,bundle);
        });
        holder.itemView.setOnLongClickListener(view -> {
            final AlertDialog.Builder dialog = new AlertDialog.Builder(view.getContext());
            dialog.setTitle("confirmar Exclusão")
                    .setMessage("Deseja excluir a tarefa: " + tarefa.getNomeTarefa() + "?")
                    .setPositiveButton("Sim", (d,w)->{
                        TarefaDAO tarefaDAO = new TarefaDAO(dialog.getContext());
                        if(tarefaDAO.excluir(tarefa)){
                            tarefaList = tarefaDAO.listar();
                            notifyItemRemoved(position);
                        }else {
                            Snackbar.make(view, "Erro ao excluir Tarefa!",Snackbar.LENGTH_LONG).show();
                        }
                    }).setNegativeButton("Não",null).show();
            return false;
        });
    }

    @Override
    public void onViewRecycled(@NonNull MyViewHolder holder) {
        holder.itemView.setOnLongClickListener(null);
        super.onViewRecycled(holder);
    }

    @Override
    public int getItemCount() {
        return tarefaList.size();
    }

     static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView tarefa;
        ImageButton shareButton;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tarefa = itemView.findViewById(R.id.textTarefa);
            shareButton = itemView.findViewById(R.id.shareButton);
        }
    }
}
