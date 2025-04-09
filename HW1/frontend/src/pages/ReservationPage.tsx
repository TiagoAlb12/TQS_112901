import { useEffect, useState } from "react";
import "./ReservationPage.css";
import { useNavigate } from "react-router-dom";

interface Reservation {
    token: string;
    restaurantName: string;
    date: string;
    checkedIn: boolean;
    cancelled: boolean;
    type: "ALMOCO" | "JANTAR";
}

export function ReservationsPage() {
    const [reservations, setReservations] = useState<Reservation[]>([]);
    const [showCancelModal, setShowCancelModal] = useState(false);
    const [cancelMessage, setCancelMessage] = useState("");
    const [selectedToken, setSelectedToken] = useState<string | null>(null);

    const navigate = useNavigate();

    useEffect(() => {
        const stored = localStorage.getItem("reservationTokens");
        if (!stored) return;
    
        const tokens: string[] = JSON.parse(stored);
    
        Promise.allSettled(tokens.map(token =>
            fetch(`http://localhost:8081/api/reservations/${token}`)
                .then(res => {
                    if (!res.ok) throw new Error("Token inválido");
                    return res.json();
                })
        )).then(results => {
            const validReservations: Reservation[] = [];
            const validTokens: string[] = [];
    
            results.forEach((r, i) => {
                if (r.status === "fulfilled") {
                    validReservations.push(r.value);
                    validTokens.push(tokens[i]);
                }
            });
    
            setReservations(validReservations);
            localStorage.setItem("reservationTokens", JSON.stringify(validTokens));
        });
    }, []);        

    const handleCancel = (token: string) => {
        fetch(`http://localhost:8081/api/reservations/${token}`, {
            method: "DELETE",
        })
            .then(() => {
                setReservations((prev) => prev.filter((r) => r.token !== token));
                setCancelMessage("Reserva cancelada com sucesso.");
                setShowCancelModal(true);

                const stored = localStorage.getItem("reservationTokens");
                const tokens: string[] = stored ? JSON.parse(stored) : [];
                const updated = tokens.filter((t) => t !== token);
                localStorage.setItem("reservationTokens", JSON.stringify(updated));
            })
            .catch((err) => {
                console.error("Erro ao cancelar reserva:", err);
                setCancelMessage("Erro ao cancelar reserva.");
                setShowCancelModal(true);
            });
    };

    return (
        <div className="reservation-list-page" style={{ paddingTop: "100px" }}>
            <h2>As Minhas Reservas</h2>
            <div className="back-button">
                <button className="button" onClick={() => navigate("/restaurants")}>
                    Voltar
                </button>
            </div>
            {reservations.length === 0 ? (
                <p>Não existem reservas ativas.</p>
            ) : (
                <table className="reservations-table">
                    <thead>
                        <tr>
                            <th>Data</th>
                            <th>Cantina</th>
                            <th>Tipo</th>
                            <th>Estado</th>
                            <th>Token</th>
                            <th>Ações</th>
                        </tr>
                    </thead>
                    <tbody>
                        {reservations.map((r, i) => (
                            <tr key={i}>
                                <td>{r.date}</td>
                                <td>{r.restaurantName}</td>
                                <td>{r.type === "ALMOCO" ? "Almoço" : "Jantar"}</td>
                                <td>
                                    {r.cancelled
                                        ? "Cancelada"
                                        : r.checkedIn
                                            ? "Utilizada"
                                            : "Ativa"}
                                </td>
                                <td>
                                    <button
                                        className="token-button"
                                        onClick={() => setSelectedToken(r.token)}
                                    >
                                        Ver Token
                                    </button>
                                </td>
                                <td>
                                    {!r.cancelled && !r.checkedIn && (
                                        <button
                                            className="cancel-button"
                                            onClick={() => handleCancel(r.token)}
                                        >
                                            Cancelar
                                        </button>
                                    )}
                                </td>
                            </tr>
                        ))}
                    </tbody>
                </table>
            )}

            {showCancelModal && (
                <div className="reservation-modal">
                    <div className="reservation-content">
                        <h3>{cancelMessage}</h3>
                        <button
                            className="reservation-btn"
                            onClick={() => setShowCancelModal(false)}
                        >
                            Fechar
                        </button>
                    </div>
                </div>
            )}

            {selectedToken && (
                <div className="reservation-modal">
                    <div className="reservation-content">
                        <h3>
                            <b>Token da Reserva</b>
                        </h3>
                        <p>{selectedToken}</p>
                        <button
                            className="reservation-btn"
                            onClick={() => setSelectedToken(null)}
                        >
                            Fechar
                        </button>
                    </div>
                </div>
            )}
        </div>
    );
}
