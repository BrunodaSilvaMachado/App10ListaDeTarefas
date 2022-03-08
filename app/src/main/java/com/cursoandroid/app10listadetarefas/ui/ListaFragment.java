package com.cursoandroid.app10listadetarefas.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.cursoandroid.app10listadetarefas.R;
import com.cursoandroid.app10listadetarefas.adapter.TarefaAdapter;
import com.cursoandroid.app10listadetarefas.databinding.FragmentListaBinding;
import com.cursoandroid.app10listadetarefas.helper.TarefaDAO;
import com.cursoandroid.app10listadetarefas.model.Tarefa;

import java.util.List;

public class ListaFragment extends Fragment {

    private FragmentListaBinding binding;
    private RecyclerView recyclerView;

    @Override
    public View onCreateView(@NonNull
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentListaBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = binding.recyclerView;
        carregarListaTarefas();
        binding.fab.setOnClickListener(view1 -> NavHostFragment.findNavController(ListaFragment.this)
                .navigate(R.id.action_FirstFragment_to_SecondFragment));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void carregarListaTarefas(){
        //Listar tarefas
        TarefaDAO tarefaDAO = new TarefaDAO(getContext());
        List<Tarefa> listTarefas = tarefaDAO.listar();

        //set adapter
        TarefaAdapter tarefaAdapter = new TarefaAdapter(listTarefas);
        //configurar RecyclerView

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        if (getActivity() == null) {
            recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        }
        recyclerView.setAdapter(tarefaAdapter);
    }

}